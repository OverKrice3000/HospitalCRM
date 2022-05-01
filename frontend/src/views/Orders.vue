<template>
  <div>
    <div>
      <div class="page-title">
        <h3>Заказы</h3>
      </div>
      <OrdersSearch @searchClick="search"/>
      <center>
        <a class="btn-floating btn-large waves-effect waves-circle waves-light blue show-form-btn" @click.prevent="showEditForm = !showEditForm">
          <i v-if="!showEditForm" class="large material-icons">arrow_drop_down</i>
          <i v-else class="large material-icons">arrow_drop_up</i>
        </a>
      </center>
      <transition name="fade">
        <OrdersForm v-if="showEditForm" :recordForEdit="recordForEdit" @addClick="addRecord" @editClick="editRecord" :medicine="medicine" :departments="departments"/>
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
import OrdersForm from '@/components/OrdersForm'
import OrdersSearch from '@/components/OrdersSearch'
export default {
  name: 'orders',
  mixins: [paginationMixin],
  data: () => ({
    loading: true,
    showEditForm: false,
    medicine: [],
    departments: [],
    supplies: [],
    headers : ['id', 'Препарат', 'Дата', 'Количество', 'Итоговая стоимость', 'Отдел'],
    recordForEdit: [],
    ids: [],
  }),
  mounted(){
    this.getAll();
  },
  methods:{
    async getAll(){
      await this.getMedicine();
      await this.getDepartments();
      await this.getRecords();
    },
    getMedicine(){
      axios.get('/api/medication/find')
      .then(response => {
        this.medicine = response.data
      })
      .catch(error => {
        console.log(error);
      });
    },
    getDepartments(){
      axios.get('/api/department/find')
      .then(response => {
        this.departments = response.data
      })
      .catch(error => {
        console.log(error);
      });
    },
    getRecords(){
      this.supplies.splice();
      axios.get('/api/supply/find')
      .then(response => {
        this.supplies = [];
        this.ids = [];
        response.data.forEach((item) => {
          this.ids.push({orderId: item.id, medicationId : item.medication.id, departmentId: item.department.id})
          this.supplies.push({
              id: item.id,
              medication: item.medication.name,
              date: item.date.slice(0,10),
              supplySize: item.supplySize,
              totalCost: item.totalCost,
              department: item.department.name,
          })
        })
        this.setupPagination(this.supplies)
      })
      .catch(error => {
        console.log(error);
      })
      .finally(() => {
        this.loading=false;
      })
    },
    addRecord(record) {
      console.log('Add order - ' + record.medicineId + ' ' + record.departmentId);
      axios.post('/api/supply/add', {
          supplySize: record.supplySize,
          date: record.date,
          medication:{
            id: record.medicineId
          },
          department:{
            id: record.departmentId
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
    editRecord(record) {
      console.log('Edit order - ' + record.id);
      let {medicationId, departmentId } = this.ids.find(item => item.orderId === record.id);
      console.log('ids = ' + medicationId + ' ' + departmentId);
      axios.put(`/api/supply/update?id=${record.id}`, {
        supplySize: record.supplySize,
        date: record.date,
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
      let {medicationId, departmentId } = this.ids.find(item => item.orderId === recordId);
      console.log('Delete - ' + medicationId + ' ' + departmentId);
      axios.delete(`/api/supply/delete?id=${recordId}`)
      .then(response => {
        this.getRecords()
        console.log(response)
      })
      .catch(error => {
        console.log(error)
      })
    },
    search(searchingFields) {
      let queryStart = (searchingFields.medication==='' && searchingFields.department==='' && searchingFields.date==='' && searchingFields.cost==='') ? '' : '?';
      let querydate = searchingFields.date ? `date=${searchingFields.date}` : '';
      let querycost = searchingFields.cost ? `${(queryStart && querydate) ? '&' : ''}cost=${searchingFields.cost}` : '';
      let querymedication = searchingFields.medication ? `${(queryStart && (querydate ||  querycost)) ? '&' : ''}medication=${searchingFields.medication}` : '';
      let querydepartment = searchingFields.department ? `${(queryStart && (querydate ||  querycost || querymedication)) ? '&' : ''}department=${searchingFields.department}` : '';
      let queryStr = `/api/supply/find${queryStart}${querydate}${querycost}${querymedication}${querydepartment}`;
      console.log(queryStr);
      axios.get(queryStr)
      .then(response => {
        this.supplies = [];
        response.data.forEach((item) => {
        this.supplies.push({
            id: item.id,
            medication: item.medication.name,
            date: item.date.slice(0,10),
            supplySize: item.supplySize,
            totalCost: item.totalCost,
            department: item.department.name,
          })
        })
        this.setupPagination(this.supplies)
      })
      .catch(error => {
        console.log(error);
      })
      .finally(() => {
        this.loading=false;
      })
    },
    pushRecordToForm(recordID){
      this.recordForEdit = this.supplies.find(record => record.id === recordID);
      console.log(this.recordForEdit);
    },
  },
  components: {
    Table, Loader, OrdersForm, OrdersSearch
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