<style scoped lang="less">
.right{
  flex: 1;
  text-align: right;
}
.search-box{
  display: flex;
  justify-content: flex-start;
  flex-wrap: wrap;
}
.button-box{
  display: flex;
  justify-content: flex-end;
  align-items: center;
}
.button-box>button{
  margin: 5px;
}
</style>
<template>
  <div>
    <div class="page-title">
      <!-- 标题面包屑 -->
      <Breadcrumb>
        <BreadcrumbItem to="/home/index">首页</BreadcrumbItem>
        <BreadcrumbItem to="/sys/log/list">{{title}}</BreadcrumbItem>
      </Breadcrumb>
    </div>
    <Form inline :model="searchData">
      <div class="page-content">
        <!-- 表格条件查询 -->
        <div class="search-box">
          <FormItem label="IP：" :label-width="110">
            <Input v-model="searchData.ip" style="width:200px" clearable />
          </FormItem>
          <!-- <FormItem label="省份：" :label-width="110">
            <Select v-model="searchData.province" style="width:200px">
                <Option v-for="item in provinceList" :value="item.value" :key="item.value">{{ item.label }}</Option>
            </Select>
          </FormItem>
          <FormItem label="城市：" :label-width="110">
            <Select v-model="searchData.city" style="width:200px">
                <Option v-for="item in cityList" :value="item.value" :key="item.value">{{ item.label }}</Option>
            </Select>
          </FormItem>-->
          <!-- <FormItem label="数据时间：" :label-width="140">
            <DatePicker v-model="searchData.startDate" type="date" placeholder="开始日期" style="width: 200px"></DatePicker>
            至
            <DatePicker v-model="searchData.endDate" type="date" placeholder="结束日期" style="width: 200px"></DatePicker>
          </FormItem> -->
          <div class="button-box right">
            <FormItem >
              <!-- 其他功能下拉框 -->
              <Dropdown trigger='click' @on-click="dropdownFunction" >
                  <Button type="success">
                      功能
                      <Icon type="md-arrow-dropdown"></Icon>
                  </Button>
                  <DropdownMenu slot="list">
                      <DropdownItem name="openAdd">
                        <div style="text-align:left">
                          <Icon type="md-add-circle" size="18" />
                          <span>新增</span>
                        </div>
                      </DropdownItem>
                      <DropdownItem divided name="openImport">
                        <div style="text-align:left">
                          <Icon type="md-cloud-upload" size="18" />
                          <span >导入Excel数据</span>
                        </div>
                      </DropdownItem>
                      <DropdownItem name="openExport">
                        <div style="text-align:left">
                          <Icon type="md-cloud-download" size="18" />
                          <span >导出Excel数据</span>
                        </div>
                      </DropdownItem>
                  </DropdownMenu>
              </Dropdown>
            </FormItem>
            <FormItem >
              <Button type="primary" @click="search">搜索</Button>
            </FormItem>
            <FormItem >
              <Button @click="cancel">取消</Button>
            </FormItem>
          </div>
        </div>
      </div>
    </Form>
    <div class="page-content">
      <!-- 表格 -->
      <t-table ref="table" :url="url.list" :param="data.param" :columns="data.columns" />
    </div>
    <!-- 详情模态窗 -->
    <d-detail ref="detail" :title="title" :getUrl="url.get" :label="exportLabel" :keyName="exportKey" />
    <!-- 新增和编辑模态窗 -->
    <form-modal ref="formModal" :title="title" :getUrl="url.get" :addUrl="url.add" :editUrl="url.edit" />
    <!-- 导入模态窗 -->
    <i-import ref="import" :getImportExcelUrl="url.importExcel" :getTemplateUrl="url.template" />
    <!-- 导出模态窗 -->
    <e-export ref="export" :getExportExcelUrl="url.exportExcel" :exportLabel="exportLabel" :exportKey="exportKey" />
    
  </div>
</template>
<script>
import formModal from "./formModal"
export default {
  name: "sysLog",  
  components: {
    formModal,
  },
  data() {
    return {
      title:"系统日志",
      //ajax调用的url汇总
      url: {
        list: "/sys/log/list",
        add: "/sys/log/add",
        get: "/sys/log/get",
        edit: "/sys/log/edit",
        del: "/sys/log/del",
        importExcel: baseURL+"/sys/log/importExcel?userId=" + JSON.parse(localStorage.getItem("user")).id,
        exportExcel: baseURL+"/sys/log/exportExcel",
        template: baseURL+"/sys/log/exportTemplete",
      },
      //表格查询字段信息
      searchData: {
      },
      //导出中文名
      exportLabel:["IP","模块名称","操作名称","操作用户","操作时间","备注"],
      //导出字段名
      exportKey:["ip","moduleName","operationName","username","createTime","remark"],
      //表格内容
      data: {
        param: {},
        columns: [
          {
            title: "操作时间",
            key: "createTime",
            minWidth:130,
            tooltip:true,
          },
          {
            title: "IP",
            key: "ip",
            minWidth: 120,
            tooltip:true,
          },
          {
            title: "模块名称",
            key: "moduleName",
            minWidth: 120,
            tooltip:true,
          },
          {
            title: "操作名称",
            key: "operationName",
            minWidth: 120,
            tooltip:true,
          },
          {
            title: "操作用户",
            key: "username",
            minWidth: 120,
            tooltip:true,
          },
          {
            title: "备注",
            key: "remark",
            minWidth: 130,
            tooltip:true,
          },
          {
            title: "操作",
            key: "options",
            fixed: 'right',
            minWidth: 200,
            render: (h, params) => {
              const edit = h("Button", {
                props: {
                  type: "warning",
                  // icon: "md-hammer",
                  size: "small"
                },
                on: {
                  click: () => {
                    this.openEdit(params.row.id)
                  }
                }
              }, '编辑');
              const search = h("Button", {
                props: {
                  type: "info",
                  // icon: "md-search",
                  size: "small"
                },
                on: {
                  click: () => {
                    this.openDetail(params.row.id)
                  }
                }
              },'查看');  
              const remove = h("Button", {
                props: {
                  type: "error",
                  // icon: "md-trash",
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
              },'删除');
              const blank = h("span", { class: "blank ivu-icon" });
              return h("div", [search, blank, remove]);
            }
          },
        ]
      }
    };
  },
  
  methods: {
    //执行表格查询
    search: function() {
      this.data.param = this.searchData;
      this.$refs.table.searchData();
    },
    //下拉框name填函数名，直接调用函数
    dropdownFunction: function(name) {
      this[name]()
    },
    //打开导入模态窗
    openImport(){
      this.$refs.import.changeShow()
    },
    //打开导出模态窗
    openExport(){
      this.$refs.export.changeShow()
    },
    //打开新增模态窗
    openAdd(){
      this.$refs.formModal.changeShow()
    },
    //打开编辑模态窗
    openEdit(id){
      this.$refs.formModal.changeShow(id)
    },
    //打开详情模态窗
    openDetail(id){
      this.$refs.detail.changeShow(id)
    },
    //取消筛选值
    cancel(){
      this.searchData={}
      this.data.param = this.searchData;
    },
    
  },
  created(){

  },
  
};
</script>
