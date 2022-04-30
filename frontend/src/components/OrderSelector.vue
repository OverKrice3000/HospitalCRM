<template>
    <el-input v-if="itemInfo" v-model="itemInfo" type="text"></el-input>
    <el-select
        v-else
        class="selector"
        v-model="values"
        filterable
        remote
        reserve-keyword
        placeholder="Выберите данные"
        :remote-method="remoteMethod"
        :loading="loading"
    >
    <el-option
      v-for="item in options"
      :key="item.value"
      :label="item.label"
      :value="item.value"
    />
  </el-select>
</template>

<script lang="js">
export default{
    props: {states: Array, itemInfo: String},
    data: () => ({
        list: [],
        options: [],
        values: [],
        loading: false,
    }),
    mounted(){
        this.list = this.states.map((item) => {
            return { value: item.name, label: item.name}
        })
        console.log(this.states);
    },
    updated(){
        this.$nextTick(function () {
            this.$emit('itemSelected', this.values);
        })
    },
    methods:{
		remoteMethod(query) {
            if (query) {
                this.loading = true
                setTimeout(() => {
                this.loading = false
                this.options = this.list.filter((item) => {
                    return item.label.toLowerCase().includes(query.toLowerCase())
                })
                }, 200)
            } else {
                this.options = []
            }
        }
	},
}
</script>

<style lang="scss">
    .selector{
        width: 100%
    }
</style>