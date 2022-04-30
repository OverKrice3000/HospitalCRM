<template>
  <div>
    <div>
    <div class="page-title">
        <h3>Врачи</h3>
    </div>
    <DoctorsSearch @searchClick="search"/>
    <center>
        <a class="btn-floating btn-large waves-effect waves-circle waves-light blue show-form-btn" @click.prevent="showEditForm = !showEditForm">
            <i v-if="!showEditForm" class="large material-icons">arrow_drop_down</i>
            <i v-else class="large material-icons">arrow_drop_up</i>
        </a>
    </center>
    <transition name="fade">
        <DoctorsForm v-if="showEditForm" :recordForEdit="recordForEdit" @addClick="addRecord" @editClick="editRecord"/>
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
import DoctorsForm from '@/components/DoctorsForm'
import DoctorsSearch from '@/components/DoctorsSearch'
export default {
  name: 'doctors',
  mixins: [paginationMixin],
  data: () => ({
    loading: true,
    showEditForm: false,
    records: [],
    headers : ['id', 'Имя', 'Фамилия', 'Специальность', 'Зарплата', 'Приемов за месяц', 'Отдел'],
    recordForEdit: [],
  }),
  mounted(){
    this.getRecords();
  },
  methods:{
    getRecords(){
       axios.get('/api/doctor/find')
      .then(response => {
        response.data.forEach(item => {
          this.records.push({
          id: item.id,
          firstName: item.firstName,
          lastName: item.lastName,
          speciality: item.speciality,
          salary: item.salary,
          numberOfPatientsDuringCurrentMonth: item.numberOfPatientsDuringCurrentMonth,
          department: item.department.name})
        });
        this.setupPagination(this.records)
      })
      .catch(error => {
        console.log(error);
      })
      .finally(() => {
        this.loading=false;
      })
    },
    addRecord(record) {
      console.log('Add - ' + record.date);
      axios.post('/api/doctor/add', {
          firstName: record.firstname,
          lastName: record.lastname,
          speciality: record.speciality,
          salary: record.salary
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
      console.log('Edit- '+ record.id);
      axios.put(`/api/doctor/update?id=${this.recordForEdit.id}`, {
          firstName: record.firstname,
          lastName: record.lastname,
          speciality: record.speciality,
          salary: record.salary
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
      axios.delete(`/api/doctor/delete?id=${recordId}`)
      .then(response => {
        this.getRecords()
        console.log(response)
      })
      .catch(error => {
        console.log(error)
      })
    },
    search(searchingFields) {
      let queryStart = (searchingFields.firstname==='' && searchingFields.lastname==='' && searchingFields.speciality==='' && searchingFields.salary==='') ? '' : '?';
      let queryFirstname = searchingFields.firstname ? `firstname=${searchingFields.firstname}` : '';
      let queryLastname = searchingFields.lastname ? `${(queryStart && queryFirstname) ? '&' : ''}lastname=${searchingFields.lastname}` : '';
      let querySpeciality = searchingFields.speciality ? `${(queryStart && (queryFirstname ||  queryLastname)) ? '&' : ''}speciality=${searchingFields.speciality}` : '';
      let querySalary = searchingFields.salary ? `${(queryStart && (queryFirstname ||  queryLastname || querySpeciality)) ? '&' : ''}salary=${searchingFields.salary}` : '';
      let queryStr = `/api/doctor/find${queryStart}${queryFirstname}${queryLastname}${querySpeciality}${querySalary}`;
      console.log(queryStr);
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
    Table, Loader, DoctorsForm, DoctorsSearch
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