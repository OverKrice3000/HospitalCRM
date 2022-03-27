<template>
  <el-form
    ref="appointmentsForm"
    :model="appointmentsForm"
    status-icon
    :rules="rules"
    label-width="120px"
    class="appointments-edit-form"
  >
    <el-form-item label="Имя пациента" prop="patientFirstname">
      <el-input v-model="appointmentsForm.patientFirstname" type="text"></el-input>
    </el-form-item>
    <el-form-item label="Фамилия пациента" prop="patientLastname">
      <el-input v-model="appointmentsForm.patientLastname" type="text"></el-input>
    </el-form-item>
    <el-form-item label="Дата приема" prop="date">
      <el-input v-model="appointmentsForm.date" type="date"></el-input>
    </el-form-item>
    <el-form-item label="Стоимость приема" prop="cost">
      <el-input v-model="appointmentsForm.cost" type="number"></el-input>
    </el-form-item>
    <el-form-item label="Фамилия доктора" prop="doctorLastname">
      <el-input v-model="appointmentsForm.doctorLastname" type="text"></el-input>
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

export default {
  props: {recordForEdit: Object},
  data() {
    return {
      isEdit: false,
      appointmentsForm: {
        patientFirstname: '',
        patientLastname: '',
        date: '',
        cost: '',
        doctorLastname: '',
      },
      rules: {
        patientFirstname: [{ required: true, message: 'Пожалуйста, введите имя пациента', trigger: 'blur' }],
        patientLastname: [{ required: true, message: 'Пожалуйста, введите фамилию пациента', trigger: 'blur' }],
        date: [{ required: true, message: 'Пожалуйста, введите дату приема', trigger: 'blur' }],
        cost: [{ required: true, message: 'Пожалуйста, введите стоимость приема', trigger: 'blur' }],
        doctorLastname: [{ required: true, message: 'Пожалуйста, введите фамилию доктора', trigger: 'blur' }],
      }
    }
  },
  updated() {
    this.$nextTick(function () {
      if (Object.keys(this.recordForEdit).length !== 0) {
        this.appointmentsForm.patientFirstname = this.recordForEdit.name; // какие ключи в базе
        this.appointmentsForm.patientLastname = this.recordForEdit.surname; 
        this.appointmentsForm.date = this.recordForEdit.age;
        this.appointmentsForm.cost = this.recordForEdit.age; 
        this.appointmentsForm.doctorLastname = this.recordForEdit.surname; 
        this.isEdit=true;
      }
    })
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.isEdit) {
            console.log('edit')
            this.$emit('editClick', this.recordForEdit);
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
        this.isEdit = false;
    }
  }
}
</script>

<style lang="scss">
.appointments-edit-form{
  margin-top: 20px;
}
</style>
