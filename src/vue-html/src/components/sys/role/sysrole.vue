<style scoped lang="less">
</style>
<template>
  <div>
    <div class="page-title">
      <Breadcrumb>
        <BreadcrumbItem to="/home">首页</BreadcrumbItem>
        <BreadcrumbItem to="/sys/menu">角色管理</BreadcrumbItem>
      </Breadcrumb>
    </div>
    <div class="page-content">
      <div>
        <Form 
          inline 
          :model="searchData">
          <FormItem label-for='search'>
            <Input 
              v-model="searchData.roleName" 
              class="input-search" 
              placeholder="名称" 
              clearable></Input>
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
    <!--<m-modal ref='editModal' :title="'编辑'" :show='editshow' :data='detail.data' :columns='detail.columns' :ok='editok'></m-modal>-->
    <Modal 
      v-model="editshow" 
      :title="'编辑'" 
      width="380" 
      @on-ok='editok' >
      <Form 
        ref='editModal' 
        :model="detail.data" 
        :rules='rule'>
        <template v-for="item in detail.columns">
          <FormItem 
            :label="item.value+'：'" 
            :key="item.id" 
            :v-model="detail.data[item.key]" 
            v-show="!!!item.hidden">
            <Select 
              v-model="detail.data[item.key]" 
              :readonly='item.readonly' 
              v-if="item.type=='select'">
              <Option 
                v-for="op in item.child" 
                :value="op.value" 
                :key="op.value">{{ op.name }}</Option>
            </Select>
            <RadioGroup 
              v-model="detail.data[item.key]" 
              v-else-if="item.type=='radio'">
              <Radio 
                v-for="op in item.child" 
                :value="op.value" 
                :key="op.value" 
                :label="op.name"/>
            </RadioGroup>
            <Input 
              v-model="detail.data[item.key]" 
              :readonly='item.readonly' 
              type="password" 
              v-else-if="item.type=='password'"></Input>
            <Input 
              v-model="detail.data[item.key]" 
              :readonly='item.readonly' 
              v-else></Input>
          </FormItem>
        </template>
        <FormItem :label="'菜单：'"/>
        <Tree 
          :data="detail.menuItems" 
          show-checkbox 
          ref="edittree"/>
      </Form>
    </Modal>

    <!--<m-modal ref='addModal' :title="'新增'" :show='addshow' :data='detail.data' :columns='add.columns' :ok='addok'></m-modal>-->
    <Modal 
      v-model="addshow" 
      :title="'新增'" 
      width="380" 
      @on-ok='addok' >
      <Form 
        ref='addModal' 
        :model="add.data" 
        :rules='rule'>
        <template v-for="item in add.columns">
          <FormItem 
            :label="item.value+'：'" 
            :key="item.id" 
            :v-model="add.data[item.key]" 
            v-show="!!!item.hidden">
            <Select 
              v-model="add.data[item.key]" 
              :readonly='item.readonly' 
              v-if="item.type=='select'">
              <Option 
                v-for="op in item.child" 
                :value="op.value" 
                :key="op.value">{{ op.name }}</Option>
            </Select>
            <RadioGroup 
              v-model="add.data[item.key]" 
              v-else-if="item.type=='radio'">
              <Radio 
                v-for="op in item.child" 
                :value="op.value" 
                :key="op.value" 
                :label="op.name"/>
            </RadioGroup>
            <Input 
              v-model="add.data[item.key]" 
              :readonly='item.readonly' 
              type="password" 
              v-else-if="item.type=='password'"></Input>
            <Input 
              v-model="add.data[item.key]" 
              :readonly='item.readonly' 
              v-else></Input>
          </FormItem>
        </template>
        <FormItem :label="'菜单：'"/>
        <Tree 
          :data="add.menuItems" 
          show-checkbox 
          ref="addtree"/>
      </Form>
    </Modal>
  </div>
</template>
<script>
export default {
  name: "Sysmenu",
  data() {
    return {
      url: {
        list: "/sys/role/list",
        add: "/sys/role/add",
        get: "/sys/role/get",
        edit: "/sys/role/edit",
        del: "/sys/role/del",
        allMenu: "/sys/menu/getAllMenu"
      },
      detail: {
        data: {},
        columns: [
          { key: "id", value: "id", hidden: true },
          { key: "roleName", value: "名称" },
          {
            key: "status",
            value: "状态",
            type: "select",
            child: [{ name: "可用", value: "1" }, { name: "禁用", value: "2" }]
          }
        ],
        menuItems: []
      },
      add: {
        data: {},
        columns: [
          { key: "id", value: "id", hidden: true },
          { key: "roleName", value: "名称" },
          {
            key: "status",
            value: "状态",
            type: "select",
            child: [{ name: "可用", value: "1" }, { name: "禁用", value: "2" }]
          }
        ],
        menuItems: []
      },
      rule: {
        roleName: [{ required: true, message: "名称必填", trigger: "blur" }]
      },
      searchData: {
        roleName: ""
      },
      editshow: false,
      addshow: false,
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
                      $this.detail.menuItems = res.data.options;
                    });
                    $this.editshow = false;
                  }
                }
              });

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
                    this.$Modal.confirm({
                        title: '确定删除吗',
                        onOk: () => {
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
                        },
                    }); 
                  }
                }
              });
              const blank = h("span", { class: "blank ivu-icon" });
              return h("div", [edit, blank, remove]);
            }
          },
          {
            title: "名称",
            key: "roleName",
            sortable: "custom"
          },
          {
            title: "状态",
            key: "status",
            sortable: "custom",
            render: (h, params) => {
              let value = "";
              if (params.row.status == 1) {
                value = "可用";
              } else if (params.row.status == 2) {
                value = "禁用";
              } else {
                value = params.row.status;
              }
              return h("div", value);
            }
          }
        ]
      }
    };
  },
  methods: {
    search: function() {
      this.data.param = this.searchData;
      this.$refs.table.searchData();
    },
    editok: function() {
      let $this = this;
      if (this.validateForm("editModal")) {
        $this.addshow = false;
        setTimeout(function() {
          $this.addshow = true;
        }, 1);
        return;
      }
      this.detail.data.resources = JSON.stringify(
        this.$refs.edittree.getCheckedNodes()
      );
      this.$ajax({
        method: "post",
        url: this.url.edit,
        data: this.detail.data
      }).then(function(res) {
        $this.$refs.table.searchData();
        $this.successModal("编辑成功");
      });
    },
    dropdownFunction: function(name) {
      if (name == "add") {
        this.addshow = true;
      }
    },
    addok: function() {
      let $this = this;
      console.log(this);
      if (this.validateForm("addModal")) {
        $this.addshow = false;
        setTimeout(function() {
          $this.addshow = true;
        }, 1);
        return;
      }
      this.add.data.resources = JSON.stringify(
        this.$refs.addtree.getCheckedNodes()
      );
      this.$ajax({
        method: "post",
        url: this.url.add,
        data: this.add.data,
        traditional: true
      }).then(function(res) {
        $this.$refs.table.searchData();
        $this.detail.data = {};
        $this.successModal("新增成功");
      });
      this.addshow = false;
    },
    validateForm: function(from) {
      let flag = false;
      console.log(this.$refs[from]);
      console.log(from);
      this.$refs[from].validate(valid => {
        if (!valid) {
          flag = true;
        }
      });
      return flag;
    }
  },
  mounted() {
    let $this = this;
    this.data.param = this.searchData;
    this.$ajax({
      method: "post",
      url: this.url.allMenu
    }).then(function(res) {
      $this.add.menuItems = res.data.data;
    });
  }
};
</script>
