<style scoped lang="less">

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
          <FormItem label-for='search'>
            <Input v-model="searchData.resourceName" class="input-search" placeholder="资源名称" clearable></Input>
          </FormItem>
          <FormItem label-for='search2'>
            <Input v-model="searchData.resourceType" class="input-search" placeholder="资源类型" clearable></Input>
          </FormItem>
          <FormItem label-for='search4'>
            <Input v-model="searchData.resourceUrl" class="input-search" placeholder="URL" clearable></Input>
          </FormItem>
          <FormItem>
            <Button type="primary" @click='search'>查询</Button>
            <Dropdown trigger="click" @on-click='dropdownFunction'>
              <Button type="primary">
                其他功能
                <Icon type="arrow-down-b"></Icon>
              </Button>
              <DropdownMenu slot="list">
                <DropdownItem name='add'>
                  <Icon type="android-add-circle" size='18' />
                  <span class="layout-text">新增</span>
                </DropdownItem>
                <DropdownItem divided>
                  <Icon type="android-upload" size='18' />
                  <span class="layout-text">导入</span>
                </DropdownItem>
                <DropdownItem>
                  <Icon type="android-download" size='18' />
                  <span class="layout-text">导出</span>
                </DropdownItem>
              </DropdownMenu>
            </Dropdown>
          </FormItem>
        </Form>
      </div>
    </div>
    <div class="page-content">
      <t-table ref='table' :url='url.list' :param='data.param' :columns='data.columns'></t-table>
    </div>
    <m-modal ref='editModal' :title="'编辑'" :show='editshow' :data='detail.data' :columns='detail.columns' :ok='editok' :rule='detail.rule'></m-modal>
    <m-modal ref='addModal' :title="'新增'" :show='addshow' :data='add.data' :columns='add.columns' :ok='addok' :rule='add.rule'></m-modal>
  </div>
</template>
<script>
export default {
  name: "sysmenu",
  data() {
    return {
      url: {
        list: "/sys/menu/list",
        add: '/sys/menu/add',
        get: '/sys/menu/get',
        edit: '/sys/menu/edit',
        del: '/sys/menu/del'
      },
      detail: {
        data: {},
        columns: [
          { key: "id", value:"id", hidden:true},
          { "key": "resourceName", "value": "名称" },
          { "key": "resourceUrl", "value": "URL" },
          { "key": "parentId", "value": "父元素id" },
          { "key": "resourceType", "value": "类型" },
          { "key": "resourceIcon", "value": "图标" },
          { "key": "resourceOrderNum", "value": "排序号" },
        ],
        rule: {
          "parentId": [{ required: true, message: "父元素id必填", trigger: 'blur' }],
          "resourceOrderNum": [{ required: true, message: "排序号必填", trigger: 'blur' }]
        }
      },
      add: {
        data: {},
        columns: [
          { key: "id", value:"id", hidden:true},
          { "key": "resourceName", "value": "名称" },
          { "key": "resourceUrl", "value": "URL" },
          { "key": "parentId", "value": "父元素id" },
          { "key": "resourceType", "value": "类型" },
          { "key": "resourceIcon", "value": "图标" },
          { "key": "resourceOrderNum", "value": "排序号" },
        ],
        rule: {
          "parentId": [{ required: true, message: "父元素id", trigger: 'blur' }],
          "resourceOrderNum": [{ required: true, message: "排序号必填", trigger: 'blur' }]
        }
      },
      searchData: {
        resourceName: '',
        resourceType: '',
        resourceUrl: '',
      },
      editshow: false,
      addshow: false,
      data: {
        param: {},
        columns: [
          {
            type: 'selection'
          },
          {
            title: '操作',
            key: 'options',
            render: (h, params) => {
              const edit = h('Button', {
                props: { type: 'ghost', shape: 'circle', icon: 'edit', size: 'small' }, on: {
                  click: () => {
                    let $this = this;
                    this.$ajax({
                      method: 'post',
                      url: $this.url.get,
                      data: {
                        id: params.row.id
                      }
                    }).then(function(res) {
                      $this.editshow = true;
                      $this.detail.data = res.data.data
                    })
                    $this.editshow = false;
                  }
                }
              });
              // const edit = h('Button', { props: { type: 'ghost', shape: 'circle', icon: 'edit', size: 'small' } });
              const remove = h('Button', {
                props: { type: 'ghost', shape: 'circle', icon: 'trash-a', size: 'small' }, on: {
                  click: () => {
                    let $this = this;
                    let array = [];
                    array.push(params.row.id)
                    this.$ajax({
                      method: 'post',
                      url: $this.url.del,
                      data: {
                        ids: array
                      }
                    }).then(function(res) {
                      $this.$refs.table.searchData()
                      $this.successModal("删除成功")
                    })
                  }
                }
              });
              const blank = h('span', { class: 'blank ivu-icon' })
              return h('div', [edit, blank, remove])
            }
          },
          {
            title: 'ID',
            key: 'id',
            sortable: 'custom'
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
            title: 'URL',
            key: 'resourceUrl'
          },
          {
            title: '状态',
            key: 'status'
          },
        ],
      },
    }
  },
  methods: {
    search: function() {
      this.data.param = this.searchData
      this.$refs.table.searchData()
    },
    editok: function() {
      let $this = this;
      if (this.$refs.editModal.validateForm()) {
        $this.editshow = false
        setTimeout(function() {
          $this.editshow = true;
        }, 1);
        return
      }
      this.$ajax({
        method: 'post',
        url: this.url.edit,
        data: this.$refs.editModal.getParams()
      }).then(function(res) {
        $this.$refs.table.searchData()
        $this.successModal("编辑成功")
      })
    },
    dropdownFunction: function(name) {
      if (name == 'add') {
        let $this = this;
        $this.addshow = false;
        setTimeout(function() {
          $this.addshow = true;
        }, 1);
      }
    },
    addok: function() {
      let $this = this;
      if (this.$refs.addModal.validateForm()) {
        $this.addshow = false
        setTimeout(function() {
          $this.addshow = true;
        }, 1);
        return
      }
      this.$ajax({
        method: 'post',
        url: this.url.add,
        data: this.$refs.addModal.getParams()
      }).then(function(res) {
        $this.$refs.table.searchData()
        $this.detail.data = {}
        $this.successModal("新增成功")
        this.addshow = false;
      })
    }

  },
  mounted() {
    this.data.param = this.searchData
  }

}
</script>
