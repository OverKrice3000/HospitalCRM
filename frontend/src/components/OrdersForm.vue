<template>
  <el-form
    ref="supplyForm"
    :model="supplyForm"
    status-icon
    :rules="rules"
    label-width="120px"
    class="supply-edit-form"
  >
    <el-form-item label="Препарат" prop="medicineData">
      <OrderSelector :states="medicine" :itemInfo="medicineValue" @itemSelected="setMedicine"/>
    </el-form-item>
    <el-form-item label="Дата" prop="date">
      <el-input v-model="supplyForm.date" type="text"></el-input>
    </el-form-item>
    <el-form-item label="Количество" prop="supplySize">
      <el-input v-model="supplyForm.supplySize" type="number"></el-input>
    </el-form-item>
    <el-form-item label="Отдел" prop="departmentData">
      <OrderSelector :states="departments" :itemInfo="departmentValue" @itemSelected="setDepartment"/>
    </el-form-item>
    <el-form-item>
      <el-button v-if="isEdit" @click="submitForm('supplyForm')" plain>Редактировать</el-button>
      <el-button v-else @click="submitForm('supplyForm')" plain>Добавить</el-button>
      <el-button @click="resetForm" plain>Очистить</el-button>
    </el-form-item>
  </el-form>
</template>

<script lang="js">
//import axios from "axios";
import OrderSelector from './OrderSelector'
export default {
  props: {recordForEdit: Object, medicine: Array, departments: Array},
  data() {
    return {
      isEdit: false,
      supplyForm: {
        id: '',
        medicineId: '',
        departmentId: '',
        date: '',
        supplySize: '',
      },
      medicineValue: '',
      departmentValue: '',
      rules: {
        medicineData: [{ required: false, message: 'Пожалуйста, укажите препарат', trigger: 'blur' }],
        date: [{ required: true, message: 'Пожалуйста, введите дату заказа', trigger: 'blur' }],
        supplySize: [{ required: true, message: 'Пожалуйста, введите количество', trigger: 'blur' }],
        departmentData: [{ required: false, message: 'Пожалуйста, укажите отдел', trigger: 'blur' }],
      }
    }
  },
  updated() {
    this.$nextTick(function () {
      if (Object.keys(this.recordForEdit).length !== 0) {
        this.supplyForm.id = this.recordForEdit.id;
        this.supplyForm.medicineId = this.medicine.find(item => this.recordForEdit.medication === item.name).id;
        this.supplyForm.departmentId = this.departments.find(item => this.recordForEdit.department === item.name).id;
        this.supplyForm.date = this.recordForEdit.date;
        this.supplyForm.supplySize = this.recordForEdit.supplySize;
        this.medicineValue = this.recordForEdit.medication;
        this.departmentValue = this.recordForEdit.department;
        this.isEdit=true;
        console.log('form - ' + this.supplyForm.date);
      }
    })
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.isEdit) {
            this.$emit('editClick', this.supplyForm);
            this.isEdit = false;
          }
          else {
            console.log('add')
            this.$emit('addClick', this.supplyForm);
          }
        } else {
          alert("Error submit!");
        }
      })
    },
    resetForm (){
        for(let i in this.supplyForm){
            this.supplyForm[i] = '';
        }
        this.medicineValue = '';
        this.departmentValue = '';
        this.isEdit = false;
    },
    setDepartment(departmentInfo){
      if(departmentInfo){
        this.supplyForm.departmentId = this.departments.find(item => departmentInfo === item.name).id;
      }
    },
    setMedicine(medicineInfo){
      if(medicineInfo){
        this.supplyForm.medicineId = this.medicine.find(item => medicineInfo === item.name).id;
      }
    },
  },
   components: {
    OrderSelector
  }
}
</script>

<style lang="scss">
.supply-edit-form{
  margin-top: 20px;
}
</style>
