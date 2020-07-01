<style scoped lang="less">
.c-content{
  height: 100%;
}

.page-content{
  height: 100%;
}
.center{
  display: flex;
  align-items:center;
  justify-content:center;
  height: calc(100% - 150px);
}
.card-bgc{
  background: rgba(0,0,0,0.3)
}
.card-title{
  text-align: center;
  font-size: 20px;
  font-weight: 1000;
}
.card-row{
  display: flex;
  // flex-flow: column;
  align-items:center;
  justify-content:center;
  flex-wrap: wrap;
  user-select:none;
  .card{
    margin: 10px;
    padding: 5px;
    width: 100px;
    text-align: center;
    &-header{
      background-color: #c9d7ea;
      padding: 10px;
    }
    &-content{
      font-size: 26px;
      font-weight: 100px;
      background-color: #c9d7ea;

    }
    &-footer{
      // background-color: #a1c0e5;
      border: 1px solid #5b9bd5;
      padding: 10px;

    }
  }
}
</style>
<template>
  <div class="c-content">
    <div class="page-title">
      <Breadcrumb>
        <BreadcrumbItem to="/home/index">首页</BreadcrumbItem>
        <!-- <BreadcrumbItem to=""></BreadcrumbItem> -->
      </Breadcrumb>
    </div>

    <div class="page-content" >
      <Row class="center" >
          <Col span="12" style="height:100%">
          </Col>
          <Col span="12" style="height:100%">
          </Col>
      </Row>
    </div>

  </div>
</template>
<script>
export default {
  name: "homeindex",
  data() {
    return {
      url: {
      },
      searchData: {},
      width: '100%',
    };
  },
  filters:{

  },
  methods: {
    
  },
  mounted() {
    
  }
};
</script>
