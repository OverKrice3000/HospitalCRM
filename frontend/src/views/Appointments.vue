<template>
  <div>
    <div>
      <div class="page-title">
        <h3>Записи на приемы</h3>
      </div>
      <AppointmentsSearch @searchClick="search"/>
      <center>
        <a class="btn-floating btn-large waves-effect waves-circle waves-light blue show-form-btn" @click.prevent="showEditForm = !showEditForm">
          <i v-if="!showEditForm" class="large material-icons">arrow_drop_down</i>
          <i v-else class="large material-icons">arrow_drop_up</i>
        </a>
      </center>
      <transition name="fade">
        <AppointmentsForm v-if="showEditForm" :recordForEdit="recordForEdit" @addClick="addRecord" @editClick="editRecord"/>
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
//import axios from "axios";
import paginationMixin from '@/mixins/paginat.mixin.js'
import Table from '@/components/Table'
import Loader from '@/components/Loader'
import AppointmentsForm from '@/components/AppointmentsForm'
import AppointmentsSearch from '@/components/AppointmentsSearch'
export default {
  name: 'appointments',
  mixins: [paginationMixin],
  data: () => ({
    loading: true,
    showEditForm: false,
    records: [
      {id : 0, name: 'dcscscs', surname: 'hcxscsxcsx', age: '20'},
      {id : 1, name: 'dscxs', surname: 'ycx', age: '20'},
      {id : 2, name: 'dscxs', surname: 'zcscxs', age: '20'},
      {id : 3, name: 'dscxs', surname: 'kcsx', age: '20'},
      {id : 4, name: 'dscsa', surname: 'fcxsacsc', age: '20'},
      {id : 5, name: 'dscsacsac', surname: 'e', age: '20'},
      {id : 72, name: 'kaban', surname: 'kabanov', age: '65'},
    ],
    headers : ['id', 'Имя пациента', 'Фамилия пациента', 'Дата приема', 'Стоимость', 'Фамилия доктора'],
    recordForEdit: [],
  }),
  mounted(){
    //const records = 
    //this.records = records.map()
    this.setupPagination(this.records);
    this.loading=false;
  },
  methods:{
    getRecords(){
      /* axios.get('/api/records')
      .then(response => {
        this.records = response.data.records
        this.setupPagination(this.records)
      })
      .catch(error => {
        console.log(error);
      })
      .finally(() => {
        this.loading=false;
      }) */
    },
    addRecord(record) {
      console.log('Add - ' + record.date);
      /* axios.post('/api/records', {
        firstname: record.firstname,
        lastname: record.lastname,
        age: record.age
      })
      .then(response => {
        this.getRecords()
        console.log(response)
      })
      .catch(error => {
        console.log(error)
      }) */
    },
    editRecord(record) {
      console.log('Edit- '+ record.id);
      /* axios.put(`/api/records/${record.id}`, {
        firstname: record.firstname,
        lastname: record.lastname,
        age: record.age
      })
      .then(response => {
        this.getRecords()
        console.log(response)
      })
      .catch(error => {
        console.log(error)
      }) */
    },
    deleteRecord(recordId){
      console.log('delete ' + recordId);
      /* axios.delete(`/api/posts/${recordID}`)
      .then(response => {
        this.getRecords()
        console.log(response)
      })
      .catch(error => {
        console.log(error)
      }) */
    },
    search(searchingFields) {
      let queryStart = (searchingFields.doctorFirstname==='' && searchingFields.doctorLastname==='' && searchingFields.patientFirstname==='' && searchingFields.patientLastname==='') ? '' : '?';
      let queryPatientFirstname = searchingFields.patientFirstname ? `firstname=${searchingFields.patientFirstname}` : '';
      let queryPatientLastname = searchingFields.patientLastname ? `${(queryStart && queryPatientFirstname) ? '&' : ''}lastname=${searchingFields.patientLastname}` : '';
      let queryDoctorFirstname = searchingFields.doctorFirstname ? `${(queryStart && (queryPatientFirstname ||  queryPatientLastname)) ? '&' : ''}doctorfirstname=${searchingFields.doctorFirstname}` : '';
      let queryDoctorLastname = searchingFields.doctorLastname ? `${(queryStart && (queryPatientFirstname ||  queryPatientLastname || queryDoctorFirstname)) ? '&' : ''}doctorlastname=${searchingFields.doctorLastname}` : '';
      let queryStr = `/api/appointments/find${queryStart}${queryPatientFirstname}${queryPatientLastname}${queryDoctorFirstname}${queryDoctorLastname}`;
      console.log(queryStr);
       /* axios.get(queryStr)
      .then(response => {
        this.records = response.data.records
        this.setupPagination(this.records)
      })
      .catch(error => {
        console.log(error);
      })
      .finally(() => {
        this.loading=false;
      }) */
    },
    pushRecordToForm(recordID){
      console.log(recordID);
      this.recordForEdit = this.records.find(record => record.id === recordID);
      console.log(this.recordForEdit.id);
    },
  },
  components: {
    Table, Loader, AppointmentsForm, AppointmentsSearch
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