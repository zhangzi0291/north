<style scoped lang="less">
</style>
<template>
  <div>
    <div class="page-title">
      <Breadcrumb>
        <BreadcrumbItem to="/home">首页</BreadcrumbItem>
        <BreadcrumbItem to="/sys/user">用户管理</BreadcrumbItem>
      </Breadcrumb>
    </div>
    <div class="page-content">
      <div>
        <Form 
          inline 
          :model="searchData">
          <FormItem label-for='search'>
            <Input 
              v-model="searchData.username" 
              class="input-search" 
              placeholder="用户名" 
              clearable></Input>
          </FormItem>
          <FormItem label-for='search2'>
            <Input 
              v-model="searchData.name" 
              class="input-search" 
              placeholder="名称" 
              clearable></Input>
          </FormItem>
          <FormItem label-for='search4'>
            <Input 
              v-model="searchData.mobile" 
              class="input-search" 
              placeholder="手机号" 
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
                <DropdownItem name='import' divided>
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

    <Modal v-model="importShow" title="Excel导入数据" :mask-closable="false" width="700">
      <Row>
        <Col span="12">
        <Upload
          :action="url.importExcel"
          accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
          :show-upload-list="false"
          :before-upload="beforeUpload"
          :on-success="uploadSuccess"
          :data="{'roleId':roleId,}"
          with-credentials
        >
        <Button icon="ios-cloud-upload-outline">导入数据</Button>
        </Upload>
        </Col>
        <Col span="12">
          <Select v-model="roleId" placeholder="请选择角色"  >
            <Option v-for="op in roleList" :value="op.value" :key="op.value">{{ op.name }}</Option>
          </Select>
        </Col>
      </Row>
      <Row></Row>
      <Row>
        <Button icon="ios-cloud-download-outline" @click="downloadTemplate1">系统用户表 模板下载</Button>
      </Row>
      <Spin fix v-if="importLoadingShow">
        <Icon type="ios-loading" size="18" class="demo-spin-icon-load"></Icon>
        <div>正在导入数据......</div>
      </Spin>
    </Modal>

  </div>
</template>
<script>
export default {
  name: "Sysuser",
  data() {
    return {
      url: {
        list: "/sys/user/list",
        add: "/sys/user/add",
        get: "/sys/user/get",
        edit: "/sys/user/edit",
        del: "/sys/user/del",
        importExcel: baseURL+"/sys/user/importExcel?userId=" + JSON.parse(localStorage.getItem("user")).id,
        template:  baseURL+"/reworkWoker/exportTemplete",
      },
      roleId:"",
      roleList:[],
      importShow:false,
      importLoadingShow:false,
      detail: {
        data: {},
        columns: [
          { key: "id", value: "id", hidden: true },
          {
            key: "name",
            value: "名称",
            rule: [{ required: true, message: "名称必填", trigger: "blur" }]
          },
          { key: "username", value: "用户名" },
          { key: "password", value: "密码", type: "password" },
          { key: "mobile", value: "手机号" },
          { key: "email", value: "电子邮件" },
          // { key: "department", value: "所在部门/合作公司" },
          { key: "expiredTime", value: "过期时间", type: "date" },
          {
            key: "roleId",
            value: "角色",
            type: "select",
            ajax: { url: "sys/role/selectOptions" }
          },
          {
            key: "status",
            value: "状态",
            type: "select",
            child: [{ name: "可用", value: "1" }, { name: "禁用", value: "2" }]
          }
        ],
        rule: {
          name: [{ required: true, message: "名称必填", trigger: "blur" }],
          username: [
            { required: true, message: "用户名必填", trigger: "blur" }
          ],
          // password: [{ required: true, message: "密码必填", trigger: "blur" }],
          roleId: [{ required: true, message: "角色必填", trigger: "blur" }],
        }
      },
      add: {
        data: {},
        columns: [
          { key: "id", value: "id", hidden: true },
          { key: "name", value: "名称" },
          { key: "username", value: "用户名" },
          { key: "password", value: "密码", type: "password" },
          { key: "mobile", value: "手机号" },
          { key: "email", value: "电子邮件" },
          { key: "expiredTime", value: "过期时间", type: "date" },
          {
            key: "roleId",
            value: "角色",
            type: "select",
            ajax: { url: "sys/role/selectOptions" }
          },
          {
            key: "status",
            value: "状态",
            type: "select",
            child: [{ name: "可用", value: "1" }, { name: "禁用", value: "2" }]
          }
        ],
        rule: {
          name: [{ required: true, message: "名称必填", trigger: "blur" }],
          username: [
            { required: true, message: "用户名必填", trigger: "blur" }
          ],
          password: [{ required: true, message: "密码必填", trigger: "blur" }],
          roleId: [{ required: true, message: "角色必填", trigger: "blur" }],
        }
      },
      searchData: {
        username: "",
        name: "",
        mobile: ""
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
            title: "名字",
            key: "name",
            sortable: "custom"
          },
          {
            title: "用户名",
            key: "username",
            sortable: "custom"
          },
          {
            title: "手机号",
            key: "mobile"
          },
          {
            title: "部门/合作公司",
            key: "department"
          },
          {
            title: "过期时间",
            key: "expiredTime",
            render: function(h, params) {
              if (params.row.expiredTime) {
                return h(
                  "div",
                  new Date(params.row.expiredTime).Format("yyyy-MM-dd hh:mm:ss")
                );
              }
            }
          },
          {
            title: "创建时间",
            key: "createTime",
            render: function(h, params) {
              return h(
                "div",
                new Date(params.row.createTime).Format("yyyy-MM-dd hh:mm:ss")
              );
            }
          },
          {
            title: "状态",
            key: "status",
            sortable: "custom"
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
    dropdownFunction: function(name) {
      if (name == "add") {
        let $this = this;
        $this.addshow = false;
        setTimeout(function() {
          $this.addshow = true;
        }, 1);
      }
      if (name == "import") {
        this.openImport()
      }
    },
    openImport(){
      this.importShow = !this.importShow
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
    },
    getRole(){
      let $this = this
      this.$ajax({
        method: "post",
        url: "sys/role/selectOptions",
        // data: val.ajax.data
      })
      .then(function(res) {
        $this.roleList = res.data.data
      });
    },
    downloadTemplate1(){
      window.location.href = this.url.template+"?fileName=系统用户表.xlsx"
    },
    beforeUpload(){
      if(!!this.roleId){
        this.importLoadingShow = true;
      }else{
         this.$Message.error("请选择角色")
        return false;
      }

    },
    uploadSuccess(response, file, fileList){
      this.$refs.table.searchData();
      var errorList = response.errorList
      var errorMessage = ''
      for (const key in errorList) {
        if (errorList.hasOwnProperty(key)) {
          const element = errorList[key];
          console.log(element)
          errorMessage += "第" + element.row + "行，数据异常，"+(!!element.rowName?element.rowName:"")+"，" + element.value+"</br>"
        }
      }
      if(errorMessage){
        this.$Notice.error({
            title: '导入数据异常',
            desc: errorMessage,
            duration: 0
        });
      }else{
        this.$Message.success("导入成功")
      }
      this.importLoadingShow = false;
    },
  },
  mounted() {
    this.data.param = this.searchData;
    this.getRole()
  }
};
</script>
