<template>
  <div>
    <div>
      <div class="page-title">
        <h3>Пациенты</h3>
      </div>
      <PatientsSearch @searchClick="search"/>
      <center>
        <a class="btn-floating btn-large waves-effect waves-circle waves-light blue show-form-btn" @click.prevent="showEditForm = !showEditForm">
          <i v-if="!showEditForm" class="large material-icons">arrow_drop_down</i>
          <i v-else class="large material-icons">arrow_drop_up</i>
        </a>
      </center>
      <transition name="fade">
        <PatientsForm v-if="showEditForm" :recordForEdit="recordForEdit" @addClick="addRecord" @editClick="editRecord"/>
     </transition>
      
      <Loader v-if="loading"/>
      <section v-else>
        <Table :records="items" :headers="headers" @editClick="pushRecordToForm" @deleteClick="deleteRecord"/>
        <el-pagination
          class="paginate"
          background
          v-model="page"
          layout="prev, pager, next"
          :page-count="pageCount"
          :current-page="+this.$route.query.page || 1"
          @current-change="pageChangeHandler"
        ></el-pagination>
      </section>
    </div>
  </div>
  
</template>

<script>
import axios from "axios";
import paginationMixin from '@/mixins/paginat.mixin.js'
import Table from '@/components/Table'
import Loader from '@/components/Loader'
import PatientsForm from '@/components/PatientsForm'
import PatientsSearch from '@/components/PatientsSearch'
export default {
  name: 'patients',
  mixins: [paginationMixin],
  data: () => ({
    loading: true,
    showEditForm: false,
    records: [],
    headers : ['id', 'Имя', 'Фамилия', 'Возраст'],
    recordForEdit: [],
  }),
  mounted(){
    this.getRecords();
  },
  methods:{
    getRecords() {
       axios.get('/api/patient/find')
      .then(response => {
        this.records = response.data
        this.setupPagination(this.records);
      })
      .catch(error => {
        console.log(error);
      })
      .finally(() => {
        this.loading=false;
      })
    },
    addRecord(record) {
      console.log('Add - ' + record.firstname);
      axios.post('/api/patient/add', { 
          firstName: record.firstname,
          lastName: record.lastname,
          age: record.age
      })
      .then(response => {
        this.getRecords()
        console.log(response)
      })
      .catch(error => {
        console.log(error)
      })
    },
    editRecord(record) {
      console.log('Edit- '+ this.recordForEdit.id + record.firstname + record.lastname + record.age);
      axios.put(`/api/patient/update?id=${this.recordForEdit.id}`, {
        firstName: record.firstname,
        lastName: record.lastname,
        age: record.age
      })
      .then(response => {
        this.getRecords()
        console.log(response)
      })
      .catch(error => {
        console.log(error)
      })
    },
    deleteRecord(recordId){
      console.log('delete ' + recordId);
      axios.delete(`/api/patient/delete?id=${recordId}`)
      .then(response => {
        this.getRecords()
        console.log(response)
      })
      .catch(error => {
        console.log(error)
      })
    },
    search(searchingFields) {
      let queryStart = (searchingFields.firstname==='' && searchingFields.lastname==='' && searchingFields.department==='') ? '' : '?';
      let queryName = searchingFields.firstname ? `firstname=${searchingFields.firstname}` : '';
      let queryLastname = searchingFields.lastname ? `${(queryStart && queryName) ? '&' : ''}lastname=${searchingFields.lastname}` : '';
      let queryDepartment = searchingFields.department ? `${(queryStart && (queryName ||  queryLastname)) ? '&' : ''}department=${searchingFields.department}` : '';
      let queryStr = `/api/patient/find${queryStart}${queryName}${queryLastname}${queryDepartment}`;
       axios.get(queryStr)
      .then(response => {
        this.records = response.data
        this.setupPagination(this.records)
      })
      .catch(error => {
        console.log(error);
      })
      .finally(() => {
        this.loading=false;
      })
    },
    pushRecordToForm(recordID){
      console.log(recordID);
      this.recordForEdit = this.records.find(record => record.id === recordID);
      console.log(this.recordForEdit.id);
    },
  },
  components: {
    Table, Loader, PatientsForm, PatientsSearch
  }
}
</script>

<style lang="scss">
  h3{
    text-align: center;
  }
  .page-title {
    -webkit-justify-content: space-between;
    -ms-flex-pack: justify;
    justify-content: space-between;
    -webkit-align-items: center;
    -ms-flex-align: center;
    align-items: center;
    padding-bottom: 1rem;
    border-bottom: solid 1px rgba(51, 51, 51, .12);
  }
  .paginate{
    margin-top: 30px;
    margin-left: 50%;
    transform: translateX(-10%);
  }
  .show-form-btn{
    margin-top: 30px;
  }
</style>