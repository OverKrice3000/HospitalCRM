<template>
  <div>
    <div>
      <div class="page-title">
        <h3>Статистика</h3>
      </div>

      <Loader v-if="loading" />
      <section v-else>
        <StatisticCard :records="records[0]" />
        <StatisticCard v-if="this.records.length > 1" :records="records[1]" />
        <Table :records="items" :headers="headers" :isStatistic="true" />
        <el-pagination
          class="paginate"
          background
          v-model="page"
          layout="prev, pager, next"
          :page-count="pageCount"
          :current-page="+this.$route.query.page || 1"
          @current-change="pageChangeHandler"
        ></el-pagination>
        <button
          @click="statisticCollect"
          class="
            deep-purple
            darken-4
            waves-effect waves-light
            btn-large
            stat-btn
          "
        >
          <i class="material-icons left">redo</i>Новый месяц
        </button>
      </section>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import paginationMixin from "@/mixins/paginat.mixin.js";
import Table from "@/components/Table";
import Loader from "@/components/Loader";
import StatisticCard from "@/components/StatisticCard";
export default {
  name: "statistic",
  mixins: [paginationMixin],
  data: () => ({
    loading: true,
    records: [
      {
        id: 0,
        date: "2022-06-22",
        incomeDepartment: "d-192031",
        outcomeDepartment: "d-31412",
        busyDepartment: "d-431412",
        busyDoctor: "Grigory London",
        income: 10000,
        outcome: 5000,
      },
      {
        id: 1,
        date: "2022-05-22",
        incomeDepartment: "d-97606",
        outcomeDepartment: "d-56463",
        busyDepartment: "d-21412",
        busyDoctor: "Vladimir Morozov",
        income: 5000,
        outcome: 10000,
      },
    ],
    headers: [
      "id",
      "Дата сбора",
      "Прибыльный отдел",
      "Убыточный отдел",
      "Нагруженный отдел",
      "Нагруженный врач",
      "Доходы",
      "Расходы",
    ],
  }),
  mounted() {
    this.getRecords();
  },
  methods: {
    getRecords() {
      axios
        .get("/api/statistics/get")
        .then((response) => {
          this.records = [];
          console.log(response);
          response.data.forEach((item) => {
            this.records.push({
              id: item.id,
              date: item.date,
              incomeDepartment: item.topIncomeDepartment.name,
              outcomeDepartment: item.topCostDepartment.name,
              busyDepartment: item.topBusyDepartment.name,
              busyDoctor:
                item.topBusyDoctor.firstName +
                " " +
                item.topBusyDoctor.lastName,
              income: item.totalIncome,
              outcome: item.totalCost,
            });
          });
          this.setupPagination(this.records);
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
          this.loading = false;
        });
    },
    statisticCollect() {
      axios
        .get("api/statistics/collect")
        .then((response) => {
          this.getRecords();
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
  components: {
    Table,
    Loader,
    StatisticCard,
  },
};
</script>

<style lang="scss">
h3 {
  text-align: center;
}
.page-title {
  -webkit-justify-content: space-between;
  -ms-flex-pack: justify;
  justify-content: space-between;
  -webkit-align-items: center;
  -ms-flex-align: center;
  align-items: center;
  padding-bottom: 1rem;
  border-bottom: solid 1px rgba(51, 51, 51, 0.12);
}
.paginate {
  margin-top: 30px;
  margin-left: 50%;
  transform: translateX(-7.5%);
}
.stat-btn {
  display: block;
  margin: 0 auto;
  margin-top: 50px;
  font-size: 1.1rem;
  font-weight: 700;
}
</style>