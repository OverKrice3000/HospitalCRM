<template>
  <el-form
    ref="appointmentsForm"
    :model="appointmentsForm"
    status-icon
    :rules="rules"
    label-width="120px"
    class="appointments-edit-form"
  >
    <el-form-item label="Пациент" prop="patientData">
      <Selector :states="patients" :personInfo="patientValue" @itemSelected="setPatient"/>
    </el-form-item>
    <el-form-item label="Дата приема" prop="date">
      <el-input v-model="appointmentsForm.date" type="text"></el-input>
    </el-form-item>
    <el-form-item label="Стоимость приема" prop="cost">
      <el-input v-model="appointmentsForm.cost" type="number"></el-input>
    </el-form-item>
    <el-form-item label="Доктор" prop="doctorData">
      <Selector :states="doctors" :personInfo="doctorValue" @itemSelected="setDoctor"/>
    </el-form-item>
    <el-form-item>
      <el-button v-if="isEdit" @click="submitForm('appointmentsForm')" plain>Редактировать</el-button>
      <el-button v-else @click="submitForm('appointmentsForm')" plain>Добавить</el-button>
      <el-button @click="resetForm" plain>Очистить</el-button>
    </el-form-item>
  </el-form>
</template>

<script lang="js">
//import axios from "axios";
import Selector from './Selector'
export default {
  props: {recordForEdit: Object, patients: Array, doctors: Array},
  data() {
    return {
      isEdit: false,
      appointmentsForm: {
        patientId: '',
        doctorId: '',
        date: '',
        cost: '',
      },
      doctorValue: '',
      patientValue: '',
      rules: {
        patientData: [{ required: false, message: 'Пожалуйста, укажите пациента', trigger: 'blur' }],
        date: [{ required: true, message: 'Пожалуйста, введите дату приема', trigger: 'blur' }],
        cost: [{ required: true, message: 'Пожалуйста, введите стоимость приема', trigger: 'blur' }],
        doctorData: [{ required: false, message: 'Пожалуйста, укажите доктора', trigger: 'blur' }],
      }
    }
  },
  updated() {
    this.$nextTick(function () {
      if (Object.keys(this.recordForEdit).length !== 0) {
        this.appointmentsForm.patientId = this.patients.find(item => this.recordForEdit.patientLastName === item.lastName).id; // какие ключи в базе
        this.appointmentsForm.doctorId = this.doctors.find(item => this.recordForEdit.doctorLastName === item.lastName).id; 
        this.appointmentsForm.date = this.recordForEdit.date;
        this.appointmentsForm.cost = this.recordForEdit.cost;
        this.doctorValue = this.recordForEdit.doctorName + ' ' + this.recordForEdit.doctorLastName;
        this.patientValue = this.recordForEdit.patientName + ' ' + this.recordForEdit.patientLastName;
        this.isEdit=true;
        console.log(this.recordForEdit);
      }
    })
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.isEdit) {
            console.log('edit')
            this.$emit('editClick', this.appointmentsForm);
            this.isEdit = false;
          }
          else {
            console.log('add')
            this.$emit('addClick', this.appointmentsForm);
          }
        } else {
          alert("Error submit!");
        }
      })
    },
    resetForm (){
        for(let i in this.appointmentsForm){
            this.appointmentsForm[i] = '';
        }
        this.doctorValue = '';
        this.patientValue = '';
        this.isEdit = false;
    },
    setPatient(patientInfo){
      if(patientInfo){
        let lastName = patientInfo.split(' ')[1]
        this.appointmentsForm.patientId = this.patients.find(item => lastName === item.lastName).id;
      }
    },
    setDoctor(doctorInfo){
      if(doctorInfo){
        let lastName = doctorInfo.split(' ')[1]
        this.appointmentsForm.doctorId = this.doctors.find(item => lastName === item.lastName).id;
      }
    },
  },
   components: {
    Selector
  }
}
</script>

<style lang="scss">
.appointments-edit-form{
  margin-top: 20px;
}
</style>
