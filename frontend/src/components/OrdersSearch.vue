<template>
  <form class="col s12 search-form">
    <div class="row">
      <div class="input-field col s2">
        <input v-model="serachFields.patientFirstname" id="patient-first-name" type="text" class="validate">
        <label for="patient-first-name">Имя пациента</label>
      </div>
      <div class="input-field col s3">
        <input v-model="serachFields.patientLastname" id="patient-last-name" type="text" class="validate">
        <label for="patient-last-name">Фамилия пациента</label>
      </div>
      <div class="input-field col s2">
        <input v-model="serachFields.doctorFirstname" id="doctor-first-name" type="text" class="validate">
        <label for="doctor-first-name">Имя доктора</label>
      </div>
      <div class="input-field col s3">
        <input v-model="serachFields.doctorLastname" id="doctor-last-name" type="text" class="validate">
        <label for="doctor-last-name">Фамилия доктора</label>
      </div>
        <a @click.prevent = "search" class="btn-floating btn-large waves-effect waves-light purple darken-1 search-btn">
            <i class="material-icons">search</i>
        </a>
    </div>
  </form>
</template>

<script>
export default{
  data: () => ({
		serachFields: {
			patientFirstname: '',
			patientLastname: '',
            doctorFirstname: '',
            doctorLastname: '',
		}
  }),
	methods:{
		search(){
		  this.$emit('searchClick', this.serachFields);
		}
	}
}
</script>

<style lang="scss">
.search-btn{
	left: 70px;
}
.input-field label {
     color: rgb(116, 30, 150);
   }
</style>