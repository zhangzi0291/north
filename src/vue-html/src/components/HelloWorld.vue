<template>
  <div class="maxheigh">
    <h1>{{ msg }}</h1>
    <h2>Essential Links</h2>
    <i-button @click='iclick'>button</i-button>
    <div class="t">
      <t-table :url='d.url' :columns='d.columns' :param='d.param'></t-table>
    </div>
  </div>
</template>

<script>

var d = {}
export default {
  name: 'HelloWorld',
  data() {
    return {
      msg: 'Welcome to Your Vue.js App',
      table1: 'table',
      d: {
        url: '/sys/getMenu',
        param: {
          orderType: 7, siteType: 1 
        },
        columns: [
          {
            type: 'selection'
          },
          {
            title: '名字',
            key: 'resourceName',
            sortable: 'custom'
          },
          {
            title: '类型',
            key: 'resourceType',
            sortable: 'custom'
          },
          {
            title: 'RUL',
            key: 'resourceUrl'
          },
          {
            title: '备注',
            key: 'resourceDesc'
          },
          {
            title: 'acceptTime',
            key: 'acceptTime',
            render: function(h, row) {
              if (this.row.acceptTime) {
                return new Date(this.row.acceptTime).Format('yyyy-MM-dd hh:mm:ss');
              }
              return;
            }
          }
        ]
      }
    }
  },
  mounted() {
  },
  methods: {
    iclick:function(){
      this.d.param={
        orderType: 8,
        siteType:2
      }
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1,
h2 {
  font-weight: normal;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: inline-block;
  margin: 0 10px;
}

a {
  color: #42b983;
}
</style>
