<template>
  <form class="col s12 search-form">
    <div class="row">
      <div class="input-field col s4">
        <input v-model="serachFields.expenses" id="expenses" type="number" class="validate">
        <label for="expenses">Мин. расходов</label>
      </div>
      <div class="input-field col s3">
        <input v-model="serachFields.income" id="income" type="number" class="validate">
        <label for="income">Мин. доходов</label>
      </div>
      <div class="input-field col s3">
        <input v-model="serachFields.patients" id="patients" type="number" class="validate">
        <label for="patients">Мин. пациентов</label>
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
			expenses: '',
			income: '',
            patients: '',
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