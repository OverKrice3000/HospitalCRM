<template>
  <el-form
    ref="doctorsForm"
    :model="doctorsForm"
    status-icon
    :rules="rules"
    label-width="120px"
    class="doctors-edit-form"
  >
    <el-form-item label="Имя" prop="firstname">
      <el-input v-model="doctorsForm.firstname" type="text"></el-input>
    </el-form-item>
    <el-form-item label="Фамилия" prop="lastname">
      <el-input v-model="doctorsForm.lastname" type="text"></el-input>
    </el-form-item>
    <el-form-item label="Специальность" prop="speciality">
      <el-input v-model="doctorsForm.speciality" type="text"></el-input>
    </el-form-item>
    <el-form-item label="Зарплата" prop="salary">
      <el-input v-model="doctorsForm.salary" type="number"></el-input>
    </el-form-item>
    <el-form-item label="Отдел" prop="department">
      <el-input v-model="doctorsForm.department" type="text"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button v-if="isEdit" @click="submitForm('doctorsForm')" plain>Редактировать</el-button>
      <el-button v-else @click="submitForm('doctorsForm')" plain>Добавить</el-button>
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
      doctorsForm: {
        firstname: '',
        lastname: '',
        speciality: '',
        salary: '',
        department: '',
      },
      rules: {
        firstname: [{ required: true, message: 'Пожалуйста, введите имя доктора', trigger: 'blur' }],
        lastname: [{ required: true, message: 'Пожалуйста, введите фамилию доктора', trigger: 'blur' }],
        speciality: [{ required: true, message: 'Пожалуйста, введите специальность', trigger: 'blur' }],
        salary: [{ required: true, message: 'Пожалуйста, введите зарплату', trigger: 'blur' }],
        department: [{ required: true, message: 'Пожалуйста, введите отдел', trigger: 'blur' }],
      }
    }
  },
  updated() {
    this.$nextTick(function () {
      if (Object.keys(this.recordForEdit).length !== 0) {
        this.doctorsForm.firstname = this.recordForEdit.firstName;
        this.doctorsForm.lastname = this.recordForEdit.lastName; 
        this.doctorsForm.speciality = this.recordForEdit.speciality;
        this.doctorsForm.salary = this.recordForEdit.salary; 
        this.doctorsForm.department = this.recordForEdit.department; 
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
            this.$emit('editClick', this.doctorsForm);
            this.isEdit = false;
          }
          else {
            console.log('add')
            this.$emit('addClick', this.doctorsForm);
          }
        } else {
          alert("Error submit!");
        }
      })
    },
    resetForm (){
        for(let i in this.doctorsForm){
            this.doctorsForm[i] = '';
        }
        this.isEdit = false;
    }
  }
}
</script>

<style lang="scss">
.doctors-edit-form{
  margin-top: 20px;
}
</style>