<template>
  <el-form
    ref="patientsForm"
    :model="patientsForm"
    status-icon
    :rules="rules"
    label-width="120px"
    class="patients-edit-form"
  >
    <el-form-item label="Имя" prop="firstname">
      <el-input v-model="patientsForm.firstname" type="text"></el-input>
    </el-form-item>
    <el-form-item label="Фамилия" prop="lastname">
      <el-input v-model="patientsForm.lastname" type="text"></el-input>
    </el-form-item>
    <el-form-item label="Возраст" prop="age">
      <el-input v-model="patientsForm.age" type="number"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button v-if="isEdit" @click="submitForm('patientsForm')" plain>Редактировать</el-button>
      <el-button v-else @click="submitForm('patientsForm')" plain>Добавить</el-button>
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
      patientsForm: {
        firstname: '',
        lastname: '',
        age: '',
      },
      rules: {
        firstname: [{ required: true, message: 'Пожалуйста, введите имя', trigger: 'blur' }],
        lastname: [{ required: true, message: 'Пожалуйста, введите фамилию', trigger: 'blur' }],
        age: [{ required: true, message: 'Пожалуйста, введите возраст', trigger: 'blur' }],
      }
    }
  },
  updated() {
    this.$nextTick(function () {
      if (Object.keys(this.recordForEdit).length !== 0) {
        this.patientsForm.firstname = this.recordForEdit.name;
        this.patientsForm.lastname = this.recordForEdit.surname;
        this.patientsForm.age = this.recordForEdit.age;
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
            this.$emit('addClick', this.patientsForm);
          }
        } else {
          alert("Error submit!");
        }
      })
    },
    resetForm (){
        for(let i in this.patientsForm){
            this.patientsForm[i] = '';
        }
        this.isEdit = false;
    }
  }
}
</script>

<style lang="scss">
.patients-edit-form{
  margin-top: 20px;
}
</style>
