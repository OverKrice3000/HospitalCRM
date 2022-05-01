<template>
  <form class="col s12 search-form">
    <div class="row">
      <div class="input-field col s2">
        <input v-model="serachFields.medication" id="medication" type="text" class="validate">
        <label for="medication">Медикамент</label>
      </div>
      <div class="input-field col s3">
        <input v-model="serachFields.department" id="department" type="text" class="validate">
        <label for="department">Отдел</label>
      </div>
      <div class="input-field col s2">
        <input v-model="serachFields.date" id="date" type="text" class="validate">
        <label for="date">Дата</label>
      </div>
      <div class="input-field col s3">
        <input v-model="serachFields.cost" id="cost" type="number" class="validate">
        <label for="cost">Мин. цена</label>
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
			medication: '',
			department: '',
      date: '',
      cost: '',
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