<template>
  <table>
    <thead>
      <tr>
        <th v-for="(head, index) in headers" :key="index">{{head}}</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="record in records" :key="record.id">
        <td v-for="(value,key) in record" :key="key.id">{{value}}</td>
        <td>
          <button class="btn-small btn blue accent-1" @click="editRecord(record.id)">
              <i class="material-icons">edit</i>
          </button>
          <button class="btn-small btn pink accent-1" @click="deleteRecord(record.id)">
              <i class="material-icons">delete</i>
          </button>
        </td>
        <!-- <td v-if="record.id==2">
          <button class="btn-small btn blue accent-1" @click="editRecord(record.id)">
              удалить
          </button>
          <button class="btn-small btn pink accent-1" @click="deleteRecord(record.id)">
              отмена
          </button>
        </td> -->
      </tr>
    </tbody>
  </table>
</template>

<script>
//import axios from "axios";
export default {
  props: {
    records: {
      required: true,
      type: Array
    },
    headers: {
      required: true,
      type: Array
    }
  },
  methods: {
    editRecord(recordID){
      this.$emit('editClick', recordID);
    },
    deleteRecord(recordID){
      this.$emit('deleteClick', recordID);
    },
  }
}
</script>