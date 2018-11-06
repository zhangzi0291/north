<template>
    <div>
    <div class="page-title">
      <Breadcrumb>
        <BreadcrumbItem to="/home">首页</BreadcrumbItem>
        <BreadcrumbItem to="/genealogy/family">家族</BreadcrumbItem>
      </Breadcrumb>
    </div>
    <div class="page-content">
      <div>
        <Form 
          inline 
          :model="searchData">
          <FormItem label-for='search'>
            <Input 
              v-model="searchData.genealogySurname" 
              class="input-search" 
              placeholder="姓氏" 
              clearable/>
          </FormItem>
          <FormItem label-for='search2'>
            <Input 
              v-model="searchData.genealogyArea" 
              class="input-search" 
              placeholder="地区" 
              clearable/>
          </FormItem>
          <FormItem>
            <Button 
              type="primary" shape="circle" 
              @click='search'>查询</Button>
            <Dropdown 
              trigger="click" 
              @on-click='dropdownFunction'>
              <Button shape="circle">
                其他功能
                <Icon type="md-arrow-dropdown"/>
              </Button>
              <DropdownMenu slot="list">
                <DropdownItem name='add'>
                  <Icon 
                    type="android-add-circle" 
                    size='18' />
                  <span class="layout-text">新增</span>
                </DropdownItem>
                <DropdownItem divided>
                  <Icon 
                    type="android-upload" 
                    size='18' />
                  <span class="layout-text">导入</span>
                </DropdownItem>
                <DropdownItem>
                  <Icon 
                    type="android-download" 
                    size='18' />
                  <span class="layout-text">导出</span>
                </DropdownItem>
              </DropdownMenu>
            </Dropdown>
          </FormItem>
        </Form>
      </div>
    </div>
    <div class="page-content">
      <t-table 
        ref='table' 
        :url='url.list' 
        :param='data.param' 
        :columns='data.columns'/>
    </div>
    <m-modal 
      ref='editModal' 
      :title="'编辑'" 
      :show='editshow' 
      :data='detail.data' 
      :columns='detail.columns' 
      :ok='editok' 
      :rule='detail.rule'/>
    <m-modal 
      ref='addModal' 
      :title="'新增'" 
      :show='addshow' 
      :data='add.data' 
      :columns='add.columns' 
      :ok='addok' 
      :rule='add.rule'/>
  </div>
</template>
<script>
export default {
  data() {
    return {
      url: {
        list: "/genealogy/family/list",
        add: "/genealogy/family/add",
        get: "/genealogy/family/get",
        edit: "/genealogy/family/edit",
        del: "/genealogy/family/del"
      },
      searchData: {
        genealogySurname: "",
        genealogyArea: ""
      },
      editshow: false,
      addshow: false,
      detail: {
        data: {},
        columns: [
          { key: "id", value: "id", hidden: true },
          { key: "genealogyArea", value: "地区" },
          { key: "genealogySurname", value: "姓氏"},
          { key: "genealogyRemark", value: "备注" },
        ],
        rule: {
          genealogyArea: [{ required: true, message: "地区必填", trigger: "blur" }],
          genealogySurname: [{ required: true, message: "姓氏必填", trigger: "blur" }]
        }
      },
      add: {
        data: {},
        columns: [
          { key: "id", value: "id", hidden: true },
          { key: "genealogyArea", value: "地区" },
          { key: "genealogySurname", value: "姓氏"},
          { key: "genealogyRemark", value: "备注" },
        ],
        rule: {
          genealogyArea: [{ required: true, message: "地区必填", trigger: "blur" }],
          genealogySurname: [{ required: true, message: "姓氏必填", trigger: "blur" }]
        }
      },
      data: {
        param: {},
        columns: [
          {
            type: "selection"
          },
          {
            title: "操作",
            key: "options",
            render: (h, params) => {
              const view = h("Button", {
                props: {
                  type: "info",
                  shape: "circle",
                  icon: "md-search",
                  size: "small"
                },
                on: {
                  click: () => {
                    let $this = this;
                    this.$router.push('/genealogy/'+params.row.id+'/topology')
                  }
                }
              });
              const edit = h("Button", {
                props: {
                  type: "info",
                  shape: "circle",
                  icon: "md-hammer",
                  size: "small"
                },
                on: {
                  click: () => {
                    let $this = this;
                    this.$ajax({
                      method: "post",
                      url: $this.url.get,
                      data: {
                        id: params.row.id
                      }
                    }).then(function(res) {
                      $this.editshow = true;
                      $this.detail.data = res.data.data;
                    });
                    $this.editshow = false;
                  }
                }
              });
              // const edit = h('Button', { props: { type: 'info', shape: 'circle', icon: 'md-hammer', size: 'small' } });
              const remove = h("Button", {
                props: {
                  type: "error",
                  shape: "circle",
                  icon: "md-trash",
                  size: "small"
                },
                on: {
                  click: () => {
                    let $this = this;
                    let array = [];
                    array.push(params.row.id);
                    this.$ajax({
                      method: "post",
                      url: $this.url.del,
                      data: {
                        ids: array
                      }
                    }).then(function(res) {
                      $this.$refs.table.searchData();
                      $this.successModal("删除成功");
                    });
                  }
                }
              });
              const blank = h("span", { class: "blank ivu-icon" });
              return h("div", [view, blank, edit, blank, remove]);
            }
          },
          {
            title: "姓氏",
            key: "genealogySurname",
            sortable: "custom"
          },
          {
            title: "地区",
            key: "genealogyArea",
            sortable: "custom"
          },
          {
            title: "备注",
            key: "genealogyRemark"
          },
        ]
      }
    };
  },
  methods:{
    search: function() {
      this.data.param = this.searchData;
      this.$refs.table.searchData(this.data.param);
    },
    dropdownFunction: function(name) {
      if (name == "add") {
        let $this = this;
        $this.addshow = false;
        setTimeout(function() {
          $this.addshow = true;
        }, 1);
      }
    },
    editok: function() {
      let $this = this;
      if (this.$refs.editModal.validateForm()) {
        $this.addshow = false;
        setTimeout(function() {
          $this.addshow = true;
        }, 1);
        return;
      }
      this.$ajax({
        method: "post",
        url: this.url.edit,
        data: this.$refs.editModal.getParams()
      }).then(function(res) {
        $this.$refs.table.searchData();
        $this.detail.data = {};
        $this.successModal("编辑成功");
      });
    },
    addok: function() {
      let $this = this;
      this.$refs.addModal.getParams();
      if (this.$refs.addModal.validateForm()) {
        $this.addshow = false;
        setTimeout(function() {
          $this.addshow = true;
        }, 1);
        return;
      }
      this.$ajax({
        method: "post",
        url: this.url.add,
        data: this.$refs.addModal.getParams()
      }).then(function(res) {
        $this.$refs.table.searchData();
        $this.add.data = {};
        $this.successModal("新增成功");
      });
      this.addshow = false;
    }
  }
};
</script>
<style lang="less" scoped>
</style>
