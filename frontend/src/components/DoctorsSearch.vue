<template>
  <form class="col s12 search-form">
    <div class="row">
      <div class="input-field col s2">
        <input v-model="serachFields.firstname" id="first-name" type="text" class="validate">
        <label for="first-name">Имя</label>
      </div>
      <div class="input-field col s3">
        <input v-model="serachFields.lastname" id="last-name" type="text" class="validate">
        <label for="last-name">Фамилия</label>
      </div>
      <div class="input-field col s3">
        <input v-model="serachFields.speciality" id="speciality" type="text" class="validate">
        <label for="speciality">Специальность</label>
      </div>
      <div class="input-field col s2">
        <input v-model="serachFields.salary" id="salary" type="number" class="validate">
        <label for="salary">Зарплата</label>
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
			firstname: '',
			lastname: '',
            speciality: '',
            salary: '',
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