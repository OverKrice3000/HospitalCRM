<template>
  <div class="row">
    <div class="col s10 m8">
      <div class="card cardstat">
        <div class="card-content black-text">
          <div class="cardstat__date">{{ this.records.date }}</div>
          <div class="row cardstat__tops">
            <div class="col s6 cardstat__tops-item">
              <i class="medium material-icons cardstat__tops-item_busy-iconhosp"
                >local_hospital</i
              >
              <p class="cardstat__tops-item_busy">Нагруженный отдел</p>
              <span>{{ this.records.busyDepartment }}</span>
            </div>
            <div class="col s6 cardstat__tops-item">
              <i class="medium material-icons cardstat__tops-item_busy-icondoc"
                >assignment_ind</i
              >
              <p class="cardstat__tops-item_busy">Нагруженный врач</p>
              <span>{{ this.records.busyDoctor }}</span>
            </div>
            <div class="col s6 cardstat__tops-item">
              <i class="medium material-icons cardstat__tops-item_success-icon"
                >attach_money</i
              >
              <p class="cardstat__tops-item_success">Прибыльный отдел</p>
              <span>{{ this.records.incomeDepartment }}</span>
            </div>
            <div class="col s6 cardstat__tops-item">
              <i class="medium material-icons cardstat__tops-item_danger-icon"
                >money_off</i
              >
              <p class="cardstat__tops-item_danger">Убыточный отдел</p>
              <span>{{ this.records.outcomeDepartment }}</span>
            </div>
          </div>
        </div>
        <div class="card-action">
          <div class="row cardstat__money">
            <div class="col s6 cardstat__income">
              <div class="cardstat__income-text">Доходы</div>
              <div class="cardstat__income-number">
                {{ this.records.income }}
              </div>
            </div>
            <div class="col s6 cardstat__outcome">
              <div class="cardstat__outcome-text">Расходы</div>
              <div class="cardstat__outcome-number">
                {{ this.records.outcome }}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    records: {
      required: true,
      type: Object,
    },
  },
};
</script>

<style lang="scss">
.cardstat {
  left: 50%;
  transform: translateX(-25%);
  margin-bottom: 50px;
  padding: 20px;
  &__date {
    font-size: 2rem;
    font-weight: 700;
    text-align: center;
    color: #512da8;
  }
  &__tops {
    margin-top: 10px;
    text-align: center;
    &-item {
      margin-top: 20px;
      transition: 0.7s ease;
      cursor: pointer;
      &:hover {
        background-color: #d8e2fedb;
        box-shadow: 0px 5px 10px 2px rgba(34, 60, 80, 0.2);
        transform: scale(1.2);
        border-radius: 20px;
      }
      &_success {
        font-size: 1.2rem;
        font-weight: 700;
        letter-spacing: 1px;
        margin-bottom: 100px;
        color: rgb(3, 99, 3);
        &-icon {
          color: rgb(72, 165, 72);
        }
      }
      &_danger {
        font-size: 1.2rem;
        font-weight: 700;
        letter-spacing: 1px;
        margin-bottom: 100px;
        color: rgb(117, 6, 6);
        &-icon {
          color: rgb(167, 44, 77);
        }
      }
      &_busy {
        font-size: 1.2rem;
        font-weight: 700;
        letter-spacing: 1px;
        margin-bottom: 100px;
        color: #98521c;
        &-iconhosp {
          color: rgb(190, 11, 11);
        }
        &-icondoc {
          color: rgb(162, 76, 10);
        }
      }
      span {
        font-size: 1.3rem;
      }
      hr {
        color: #673ab7;
      }
    }
  }
  &__money {
    display: flex;
    justify-content: space-between;
    transition: 0.7s ease;
    cursor: pointer;
    &:hover {
      background-color: #dfc1f4b4;
      box-shadow: 0px 5px 10px 2px rgba(34, 60, 80, 0.2);
      transform: scale(1.1);
      border-radius: 20px;
    }
  }
  &__income {
    text-align: center;
    &-text {
      color: rgb(3, 99, 3);
      font-size: 2rem;
      font-weight: 500;
      font-style: italic;
    }
    &-number {
      font-size: 1.2rem;
      font-weight: 500;
    }
  }
  &__outcome {
    text-align: center;
    &-text {
      color: rgb(117, 6, 6);
      font-size: 2rem;
      font-weight: 500;
      font-style: italic;
    }
    &-number {
      font-size: 1.2rem;
      font-weight: 500;
    }
  }
}
</style>