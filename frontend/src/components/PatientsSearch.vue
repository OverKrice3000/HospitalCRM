<template>
  <form class="col s12 search-form">
    <div class="row">
      <div class="input-field col s3">
        <input v-model="serachFields.firstname" id="first-name" type="text" class="validate">
        <label for="first-name">Имя</label>
      </div>
      <div class="input-field col s4">
        <input v-model="serachFields.lastname" id="last-name" type="text" class="validate">
        <label for="last-name">Фамилия</label>
      </div>
      <div class="input-field col s3">
        <input v-model="serachFields.department" id="department" type="text" class="validate">
        <label for="department">Отдел</label>
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
      department: '',
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