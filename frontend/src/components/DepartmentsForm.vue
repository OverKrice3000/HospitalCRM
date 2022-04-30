<template>
  <el-form
    ref="departmentsForm"
    :model="departmentsForm"
    status-icon
    :rules="rules"
    label-width="120px"
    class="departments-edit-form"
  >
    <el-form-item label="Название отдела" prop="name">
      <el-input v-model="departmentsForm.name" type="text"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button v-if="isEdit" @click="submitForm('departmentsForm')" plain>Редактировать</el-button>
      <el-button v-else @click="submitForm('departmentsForm')" plain>Добавить</el-button>
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
      departmentsForm: {
        name: '',
      },
      rules: {
        name: [{ required: true, message: 'Пожалуйста, введите название отдела', trigger: 'blur' }],
      }
    }
  },
  updated() {
    this.$nextTick(function () {
      if (Object.keys(this.recordForEdit).length !== 0) {
        this.departmentsForm.name = this.recordForEdit.name;
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
            this.$emit('editClick', this.departmentsForm);
            this.isEdit = false;
          }
          else {
            console.log('add')
            this.$emit('addClick', this.departmentsForm);
          }
        } else {
          alert("Error submit!");
        }
      })
    },
    resetForm (){
        for(let i in this.departmentsForm){
            this.departmentsForm[i] = '';
        }
        this.isEdit = false;
    }
  }
}
</script>

<style lang="scss">
.departments-edit-form{
  margin-top: 20px;
}
</style>