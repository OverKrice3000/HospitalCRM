<template>
  <div>
    <div>
      <div class="page-title">
        <h3>Отделы</h3>
      </div>
      <DepartmentsSearch @searchClick="search"/>
      <center>
        <a class="btn-floating btn-large waves-effect waves-circle waves-light blue show-form-btn" @click.prevent="showEditForm = !showEditForm">
          <i v-if="!showEditForm" class="large material-icons">arrow_drop_down</i>
          <i v-else class="large material-icons">arrow_drop_up</i>
        </a>
      </center>
      <transition name="fade">
        <DepartmentsForm v-if="showEditForm" :recordForEdit="recordForEdit" @addClick="addRecord" @editClick="editRecord"/>
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
import DepartmentsForm from '@/components/DepartmentsForm'
import DepartmentsSearch from '@/components/DepartmentsSearch'
export default {
  name: 'medicine',
  mixins: [paginationMixin],
  data: () => ({
    loading: true,
    showEditForm: false,
    records: [],
    headers : ['id', 'Название', 'Пациентов за месяц', 'Доходы за месяц', 'Расходы за месяц'],
    recordForEdit: [],
  }),
  mounted(){
    this.getRecords();
  },
  methods:{
    getRecords() {
       axios.get('/api/department/find')
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
      this.setupPagination(this.records);
      this.loading=false;
    },
    addRecord(record) {
      axios.post('/api/department/add', { 
          name: record.name,
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
      axios.put(`/api/department/update?id=${this.recordForEdit.id}`, {
        name: record.name,
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
      axios.delete(`/api/department/delete?id=${recordId}`)
      .then(response => {
        this.getRecords()
        console.log(response)
      })
      .catch(error => {
        console.log(error)
      })
    },
    search(searchingFields) {
      let queryStart = (searchingFields.expenses==='' && searchingFields.income==='' && searchingFields.patients==='') ? '' : '?';
      let queryExpenses = searchingFields.expenses ? `costs=${searchingFields.expenses}` : '';
      let queryIncome = searchingFields.income ? `${(queryStart && queryExpenses) ? '&' : ''}incomes=${searchingFields.income}` : '';
      let queryPatients = searchingFields.patients ? `${(queryStart && (queryExpenses ||  queryIncome)) ? '&' : ''}patients=${searchingFields.patients}` : '';
      let queryStr = `/api/department/find${queryStart}${queryExpenses}${queryIncome}${queryPatients}`;
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
    Table, Loader, DepartmentsForm, DepartmentsSearch
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