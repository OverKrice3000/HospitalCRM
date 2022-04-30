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
        <AppointmentsForm v-if="showEditForm" :recordForEdit="recordForEdit" @addClick="addRecord" @editClick="editRecord" :patients="patients" :doctors="doctors"/>
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
import AppointmentsForm from '@/components/AppointmentsForm'
import AppointmentsSearch from '@/components/AppointmentsSearch'
export default {
  name: 'appointments',
  mixins: [paginationMixin],
  data: () => ({
    loading: true,
    showEditForm: false,
    patients: [],
    doctors: [],
    appointments: [],
    headers : ['Номер записи', 'Имя пациента', 'Фамилия пациента', 'Дата', 'Стоимость', 'Имя доктора', 'Фамилия доктора'],
    recordForEdit: [],
    ids: [],
  }),
  mounted(){
    this.getAll();
  },
  methods:{
    async getAll(){
      await this.getPatients();
      await this.getDcotors();
      await this.getRecords();
    },
    getPatients(){
      axios.get('/api/patient/find')
      .then(response => {
        this.patients = response.data
      })
      .catch(error => {
        console.log(error);
      });
    },
    getDcotors(){
      axios.get('/api/doctor/find')
      .then(response => {
        this.doctors = response.data
      })
      .catch(error => {
        console.log(error);
      });
    },
    getRecords(){
      this.appointments.splice();
      axios.get('/api/appointment/find')
      .then(response => {
        this.appointments = [];
        this.ids = [];
        let counter = 0;
        response.data.forEach((item) => {
          let patient = this.patients.find(patient => patient.id === item.id.patientId);
          let doctor = this.doctors.find(doctor => doctor.id === item.id.doctorId);
          this.ids.push({patientId : item.id.patientId, doctorId: item.id.doctorId})
          this.appointments.push({
              id: ++counter,
              patientName: patient.firstName,
              patientLastName: patient.lastName,
              date: item.date.slice(0,10),
              cost: item.cost,
              doctorName: doctor.firstName,
              doctorLastName: doctor.lastName,
          })
        })
        this.setupPagination(this.appointments)
      })
      .catch(error => {
        console.log(error);
      })
      .finally(() => {
        this.loading=false;
      })
    },
    addRecord(record) {
      console.log('Add appointment - ' + record);
      axios.post('/api/appointment/add', {
          id: {
            doctorId: record.doctorId,
            patientId: record.patientId,
          },
          date: record.date,
          cost: record.cost
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
      console.log('Edit appointment - ' + record);
      axios.put(`/api/appointment/update`, {
        id: {
          doctorId: record.doctorId,
          patientId: record.patientId,
        },
        date: record.date,
        cost: record.cost
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
      axios.delete(`/api/appointment/delete`, {
        data: {
            doctorId: this.ids[recordId - 1].doctorId,
            patientId:  this.ids[recordId - 1].patientId,
        }
      })
      .then(response => {
        this.getRecords()
        console.log(response)
      })
      .catch(error => {
        console.log(error)
      })
    },
    search(searchingFields) {
      let queryStart = (searchingFields.doctorFirstname==='' && searchingFields.doctorLastname==='' && searchingFields.patientFirstname==='' && searchingFields.patientLastname==='') ? '' : '?';
      let queryPatientFirstname = searchingFields.patientFirstname ? `patientfirstname=${searchingFields.patientFirstname}` : '';
      let queryPatientLastname = searchingFields.patientLastname ? `${(queryStart && queryPatientFirstname) ? '&' : ''}patientlastname=${searchingFields.patientLastname}` : '';
      let queryDoctorFirstname = searchingFields.doctorFirstname ? `${(queryStart && (queryPatientFirstname ||  queryPatientLastname)) ? '&' : ''}doctorfirstname=${searchingFields.doctorFirstname}` : '';
      let queryDoctorLastname = searchingFields.doctorLastname ? `${(queryStart && (queryPatientFirstname ||  queryPatientLastname || queryDoctorFirstname)) ? '&' : ''}doctorlastname=${searchingFields.doctorLastname}` : '';
      let queryStr = `/api/appointment/find${queryStart}${queryPatientFirstname}${queryPatientLastname}${queryDoctorFirstname}${queryDoctorLastname}`;
      console.log(queryStr);
      axios.get(queryStr)
      .then(response => {
        this.appointments = [];
        response.data.forEach((item) => {
          let patient = this.patients.find(patient => patient.id === item.id.patientId);
          let doctor = this.doctors.find(doctor => doctor.id === item.id.doctorId);
          this.appointments.push({
              patientName: patient.firstName,
              patientLastName: patient.lastName,
              date: item.date.slice(0,10),
              cost: item.cost,
              doctorName: doctor.firstName,
              doctorLastName: doctor.lastName,
          })
        })
        this.setupPagination(this.appointments)
      })
      .catch(error => {
        console.log(error);
      })
      .finally(() => {
        this.loading=false;
      })
    },
    pushRecordToForm(recordID){
      this.recordForEdit = this.appointments.find(record => record.id === recordID);
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