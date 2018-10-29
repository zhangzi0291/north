<template>
  <div>
    <Table 
      ref="table" 
      :loading="tableLoad" 
      :columns="columns" 
      :data="data" 
      border 
      :height="tableHeight" 
      @on-sort-change="sort"
      @on-row-dblclick="functions.onRowDblclick"/>
    <br>
    <Page 
      :total="page.total" 
      :current="page.current" 
      show-sizer 
      show-elevator 
      show-total 
      @on-change="change" 
      @on-page-size-change="chageSize"/>
  </div>
</template>

<script>
export default {
  name: "Mtable",
  data() {
    return {
      height: 300,
      page: {
        current: 1,
        pageSize: 10
      },
      data: [],
      tableLoad: true,
      tableHeight: 521,
      ascs: new Set(),
      descs: new Set()
    };
  },
  props: {
    url: {
      type: String
    },
    param: {
      type: Object
    },
    columns: {
      type: Array
    },
    functions:{
      type:Object,
      default:function(){
        return {
          onRowDblclick:function(){
            
          },
        }
      }
    }
  },
  mounted: function() {
    this.searchData(this.param, this.page.current, this.page.pageSize);
    this.changeHeight();
    window.onresize = () => {
      return (() => {
        this.changeHeight();
      })();
    };
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
      this.param.current = page;
      this.searchData(this.param, page, this.page.pageSize);
    },
    chageSize: function(pageSize) {
      this.tableLoad = true;
      this.param.size = pageSize;
      this.searchData(this.param, this.page.current, pageSize);
    },
    changeHeight: function() {
      let nowHeight = window.innerHeight - this.$refs.table.$el.offsetTop - 160;
      if (nowHeight < 521) {
        this.tableHeight = nowHeight;
      } else {
        this.tableHeight = 521;
      }
    },
    searchData: function(param, current, pageSize) {
      var $this = this;
      if (!param) {
        param = {};
      }
      if (!pageSize) {
        console.log(current, pageSize);
        this.param.size = pageSize;
      }
      if (!current) {
        this.param.current = current;
      }
      this.param = Object.assign(this.param, param);
      this.$ajax({
        method: "post",
        url: this.url,
        data: this.param
      })
        .then(function(res) {
          $this.page.total = res.data.total;
          $this.data = res.data.rows;
          $this.tableLoad = false;
        })
        .catch(function(error) {
          $this.tableLoad = false;
        });
    },
    sort: function(sortOrder) {
      console.log(sortOrder);
      this.tableLoad = true;
      let key = this.toLine(sortOrder.key);
      switch (sortOrder.order) {
        case "normal":
          this.ascs.clear();
          this.descs.clear();
          break;
        case "asc":
          this.ascs.add(key);
          this.descs.clear();
          break;
        case "desc":
          this.descs.add(key);
          this.ascs.clear();
          break;
        default:
          break;
      }
      this.param.ascs = Array.from(this.ascs);
      this.param.descs = Array.from(this.descs);
      this.searchData(this.param, this.page.current, this.page.pageSize);
    }
  }
};
</script>

<style scoped>
</style>
