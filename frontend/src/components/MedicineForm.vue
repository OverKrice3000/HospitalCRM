<template>
  <el-form
    ref="medicineForm"
    :model="medicineForm"
    status-icon
    :rules="rules"
    label-width="120px"
    class="doctors-edit-form"
  >
    <el-form-item label="Название" prop="name">
      <el-input v-model="medicineForm.name" type="text"></el-input>
    </el-form-item>
    <el-form-item label="Производитель" prop="producer">
      <el-input v-model="medicineForm.producer" type="text"></el-input>
    </el-form-item>
    <el-form-item label="Цена" prop="cost">
      <el-input v-model="medicineForm.cost" type="number"></el-input>
    </el-form-item>
    <!-- <el-form-item label="Отдел" prop="department">
      <el-input v-model="medicineForm.department" type="text"></el-input>
    </el-form-item> -->
    <el-form-item>
      <el-button v-if="isEdit" @click="submitForm('medicineForm')" plain>Редактировать</el-button>
      <el-button v-else @click="submitForm('medicineForm')" plain>Добавить</el-button>
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
      medicineForm: {
        name: '',
        producer: '',
        cost: '',
      },
      rules: {
        name: [{ required: true, message: 'Пожалуйста, введите название препарата', trigger: 'blur' }],
        producer: [{ required: true, message: 'Пожалуйста, введите производителя', trigger: 'blur' }],
        cost: [{ required: true, message: 'Пожалуйста, введите стоимость', trigger: 'blur' }],
      }
    }
  },
  updated() {
    this.$nextTick(function () {
      if (Object.keys(this.recordForEdit).length !== 0) {
        this.medicineForm.name = this.recordForEdit.name;
        this.medicineForm.producer = this.recordForEdit.vendor; 
        this.medicineForm.cost = this.recordForEdit.cost;
        this.isEdit=true;
      }
    })
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.isEdit) {
            console.log('edit - ' + this.medicineForm);
            this.$emit('editClick', this.medicineForm);
            this.isEdit = false;
          }
          else {
            console.log('add')
            this.$emit('addClick', this.medicineForm);
          }
        } else {
          alert("Error submit!");
        }
      })
    },
    resetForm (){
        for(let i in this.medicineForm){
            this.medicineForm[i] = '';
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