<style scoped lang="less">
/deep/ .tree-render .ivu-tree-title {
  width: 100%;
}
</style>
<template>
  <div>
    <div class="page-title">
      <Breadcrumb>
        <BreadcrumbItem to="/home">首页</BreadcrumbItem>
        <BreadcrumbItem to="/sys/menu">菜单管理</BreadcrumbItem>
      </Breadcrumb>
    </div>
    <div class="page-content">
      <div>
        <Form inline :model="searchData">
          
          <FormItem>
            <!-- <Button type="primary" shape="circle" @click="search">查询</Button> -->
            <Dropdown trigger="click" @on-click="dropdownFunction">
              <Button shape="circle">
                其他功能
                <Icon type="md-arrow-dropdown" />
              </Button>
              <DropdownMenu slot="list">
                <DropdownItem name="add">
                  <Icon type="md-add-circle" size="18" />
                  <span class="layout-text">新增</span>
                </DropdownItem>
                <DropdownItem divided>
                  <Icon type="md-cloud-upload" size="18" />
                  <span class="layout-text">导入</span>
                </DropdownItem>
                <DropdownItem>
                  <Icon type="md-cloud-download" size="18" />
                  <span class="layout-text">导出</span>
                </DropdownItem>
              </DropdownMenu>
            </Dropdown>
          </FormItem>
        </Form>
      </div>
    </div>
    <div class="page-content">
      <!-- <t-table 
        ref='table' 
        :url='url.list' 
        :param='data.param' 
      :columns='data.columns'/>-->
      <Tree
        :data="menuData"
        children-key="children"
        :render="render"
        show-checkbox
        class="tree-render"
      ></Tree>
    </div>
    <m-modal
      ref="editModal"
      :title="'编辑'"
      :show="editshow"
      :data="detail.data"
      :columns="detail.columns"
      :ok="editok"
      :rule="detail.rule"
    />
    <m-modal
      ref="addModal"
      :title="'新增'"
      :show="addshow"
      :data="add.data"
      :columns="add.columns"
      :ok="addok"
      :rule="add.rule"
    />
  </div>
</template>
<script>
export default {
  name: "Sysmenu",
  data() {
    return {
      url: {
        list: "/sys/menu/list",
        getAllMenu: "/sys/menu/getAllResource",
        add: "/sys/menu/add",
        get: "/sys/menu/get",
        edit: "/sys/menu/edit",
        del: "/sys/menu/del"
      },
      menuData: [],
      detail: {
        data: {},
        columns: [
          { key: "id", value: "id", hidden: true },
          { key: "resourceName", value: "名称" },
          { key: "resourceUrl", value: "URL" },
          { key: "parentId", value: "父元素id" },
          { key: "resourceType", value: "类型" },
          { key: "permission", value: "所需权限" },
          { key: "resourceIcon", value: "图标" },
          { key: "resourceOrderNum", value: "排序号" }
        ],
        rule: {
          parentId: [
            { required: true, message: "父元素id必填", trigger: "blur" }
          ],
          resourceOrderNum: [
            {
              required: true,
              type: "nubmer",
              message: "排序号必填",
              trigger: "blur"
            }
          ]
        }
      },
      add: {
        data: {},
        columns: [
          { key: "id", value: "id", hidden: true },
          { key: "resourceName", value: "名称" },
          { key: "resourceUrl", value: "URL" },
          { key: "parentId", value: "父元素id" },
          { key: "resourceType", value: "类型" },
          { key: "permission", value: "所需权限" },
          { key: "resourceIcon", value: "图标" },
          { key: "resourceOrderNum", value: "排序号" }
        ],
        rule: {
          parentId: [
            { required: true, message: "父元素id必填", trigger: "blur" }
          ],
          resourceOrderNum: [
            { required: true, message: "排序号必填", trigger: "blur" }
          ]
        }
      },
      searchData: {
        resourceName: "",
        resourceType: "",
        resourceUrl: ""
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
                    this.$Modal.confirm({
                      title: "确定删除吗",
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
                      }
                    });
                  }
                }
              });
              const blank = h("span", { class: "blank ivu-icon" });
              return h("div", [edit, blank, remove]);
            }
          },
          {
            title: "ID",
            key: "id",
            sortable: "custom"
          },
          {
            title: "名字",
            key: "resourceName",
            sortable: "custom"
          },
          {
            title: "类型",
            key: "resourceType",
            sortable: "custom"
          },
          {
            title: "URL",
            key: "resourceUrl"
          },
          {
            title: "状态",
            key: "status"
          }
        ]
      },
      render: (h, params) => {
        const add = h("Button", {
          props: {
            type: "success",
            shape: "circle",
            icon: "md-add",
            size: "small"
          },
          on: {
            click: () => {
              let $this = this;
              $this.addshow = false;
                setTimeout(function() {
                  $this.addshow = true;
                  $this.add.data = {
                    parentId:params.data.id
                  }
                }, 1);
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
                  id: params.data.id
                }
              }).then(function(res) {
                $this.editshow = true;
                $this.detail.data = res.data.data;
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
                title: "确定删除吗",
                onOk: () => {
                  let array = [];
                  array.push(params.data.id);
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
              });
            }
          }
        });
        const blank = h("span", { class: "blank ivu-icon" });
        let button;
        if(params.data.parentId == -1){
          button = [params.data.title, h("div", [add, blank, edit, blank, remove])]
        }else{
          button = [params.data.title, h("div", [edit, blank, remove])]
        }
        return h(
          "div",
          {
            style: {
              display: "flex",
              "justify-content": "space-between",
              width: "calc(100% - 40px)"
            }
          },
          button
        );
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
      if (this.$refs.editModal.validateForm()) {
        $this.editshow = false;
        setTimeout(function() {
          $this.editshow = true;
        }, 1);
        return;
      }
      this.$ajax({
        method: "post",
        url: this.url.edit,
        data: this.$refs.editModal.getParams()
      }).then(function(res) {
        $this.getAllMenu()
        $this.successModal("编辑成功");
      });
    },
    dropdownFunction: function(name) {
      if (name == "add") {
        let $this = this;
        $this.addshow = false;
        $this.add.data = {
          parentId:-1
        }
        setTimeout(function() {
          $this.addshow = true;
        }, 1);
      }
    },
    addok: function() {
      let $this = this;
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
        $this.getAllMenu()
        $this.detail.data = {};
        $this.successModal("新增成功");
        this.addshow = false;
      });
    },
    getAllMenu: function() {
      let $this = this;
      this.$ajax({
        method: "post",
        url: this.url.getAllMenu
      }).then(function(res) {
        console.log(res.data.data);
        $this.menuData = res.data.data;
        $this.detail.data = {};
      });
    },

  },
  mounted() {
    this.data.param = this.searchData;
    this.getAllMenu();
  }
};
</script>
