<template>
  <div>
    <Table ref="table" :loading="tableLoad" :columns="columns" :data="data" border :height="tableHeight" @on-sort-change="sort"></Table>
    <br>
    <Page :total="page.total" :current="page.current" show-sizer show-elevator show-total @on-change="change" @on-page-size-change="chageSize"></Page>
  </div>
</template>

<script>
export default {
  name: 'mtable',
  data() {
    return {
      height: 300,
      page: {
        current: 1,
        pageSize: 10
      },
      data: [],
      tableLoad: true,
      tableHeight: 521
    }
  },
  props: {
    url: {
      type: String
    },
    param: {
      type: Object,
    },
    columns: {
      type: Array
    }
  },
  mounted: function() {
    this.searchData(this.param, this.page.current, this.page.pageSize)
    this.changeHeight()
    window.onresize = () => {
      return (() => {
        this.changeHeight()
      })()
    }
  },
  watch: {
    // param: {
    //   handler: function(val, oldVal) {
    //     this.tableLoad = true;
    //     this.searchData(this.param, this.page.current, this.page.pageSize)
    //   },
    //   deep: true
    // },
  },
  methods: {
    change: function(page) {
      this.tableLoad = true;
      this.param.offset = page - 1
      this.searchData(this.param, page, this.page.pageSize)
    },
    chageSize: function(pageSize) {
      this.tableLoad = true;
      this.param.limit = pageSize
      this.searchData(this.param, this.page.current, pageSize)
    },
    changeHeight: function() {
      let nowHeight = window.innerHeight - this.$refs.table.$el.offsetTop - 160;
      if (nowHeight < 521) {
        this.tableHeight = nowHeight
      } else{
        this.tableHeight = 521
      }
    },
    searchData: function(param, current, pageSize) {
      var $this = this
      if (!param) {
        param = {}
      }
      if (!pageSize) {
        this.param.limit = pageSize
      }
      if (!current) {
        this.param.offset = current - 1
      }
      this.param = Object.assign(this.param, param);
      this.$ajax({
        method: 'post',
        url: this.url,
        data: this.param
      }).then(function(res) {
        $this.page.total = res.data.total
        $this.data = res.data.rows
        $this.tableLoad = false
      }).catch(function(error) {
        $this.tableLoad = false
      });
    },
    sort: function(sortOrder) {
      console.log(sortOrder)
      this.tableLoad = true;
      this.param.order = sortOrder.order
      this.param.sortCol = sortOrder.key
      this.searchData(this.param, this.page.current, this.page.pageSize)
    }
  }
}
</script>

<style scoped>

</style>
