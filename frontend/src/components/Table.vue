<template>
  <table class="data-table highlight">
    <thead>
      <tr>
        <th v-for="(head, index) in headers" :key="index">{{ head }}</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="record in records" :key="record.id">
        <td v-for="(value, key) in record" :key="key.id">{{ value }}</td>
        <td v-if="!isStatistic">
          <button
            class="btn-small btn blue accent-1"
            @click="editRecord(record.id)"
          >
            <i class="material-icons">edit</i>
          </button>
          <el-popconfirm
            confirm-button-text="Да"
            cancel-button-text="Нет"
            :icon="InfoFilled"
            icon-color="red"
            title="Вы точно хотите удалить эту запись?"
            @confirm="deleteRecord(record.id)"
          >
            <template #reference>
              <button class="btn-small btn pink accent-1">
                <i class="material-icons">delete</i>
              </button>
            </template>
          </el-popconfirm>
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
import { InfoFilled } from "@element-plus/icons-vue";
export default {
  props: {
    records: {
      required: true,
      type: Array,
    },
    headers: {
      required: true,
      type: Array,
    },
    isStatistic: {
      type: Boolean,
    },
  },
  methods: {
    editRecord(recordID) {
      this.$emit("editClick", recordID);
    },
    deleteRecord(recordID) {
      this.$emit("deleteClick", recordID);
    },
  },
};
</script>

<style lang="scss">
.data-table {
  border: none;
  margin-top: 20px;
  border-collapse: separate;
}

.data-table thead th {
  font-weight: bold;
  text-align: left;
  border: none;
  padding: 10px 15px;
  background: #ede7f6;
  font-size: 16px;
  border-top: 1px solid #b39ddb;
}

.data-table tr th:first-child,
.data-table tr td:first-child {
  border-left: 1px solid #b39ddb;
}

.data-table tr th:last-child,
.data-table tr td:last-child {
  border-right: 1px solid #b39ddb;
}

.data-table thead tr th:first-child {
  border-radius: 30px 0 0 0;
}

.data-table thead tr th:last-child {
  border-radius: 0 30px 0 0;
}

.data-table tbody td {
  text-align: left;
  border: none;
  padding: 10px 15px;
  font-size: 16px;
  vertical-align: top;
}

.data-table tbody tr:last-child td {
  border-bottom: 1px solid #b39ddb;
}

.data-table tbody tr:last-child td:first-child {
  border-radius: 0 0 0 30px;
}

.data-table tbody tr:last-child td:last-child {
  border-radius: 0 0 30px 0;
}
</style>