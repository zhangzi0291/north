<style scoped lang="less">
.demo-spin-icon-load {
  animation: ani-demo-spin 1s linear infinite;
}
.right {
  // position: absolute;
  // right: 30px;
  float: right;
}
</style>
<template>
  <div>
    <div class="page-title">
      <Breadcrumb>
        <BreadcrumbItem to="/home/index">首页</BreadcrumbItem>
        <BreadcrumbItem to="/statchart/list	">统计数据</BreadcrumbItem>
      </Breadcrumb>
    </div>
    <Form inline :model="searchData">
      <div class="page-content">
        <FormItem>
          <Input
            search
            placeholder="请输入项目名进行搜索，可以直接回车搜索"
            v-model="searchData.projectGroup"
            style="width: 320px"
            @on-search="search"
            clearable
          />
        </FormItem>
        <FormItem>
          <Button type="primary" @click="search">
            <Icon type="md-search" />搜索
          </Button>
        </FormItem>
        <FormItem>
          <Button type="primary" @click="enableProSearch = !enableProSearch">
            <Icon type="md-arrow-round-up" v-if="enableProSearch" />
            <Icon type="md-arrow-round-down" v-else />高级搜索
          </Button>
        </FormItem>
        <div class="right">
          <FormItem>
            <Button type="success" @click="openImport()">
              <Icon type="md-cloud-upload" />Excel导入数据
            </Button>
          </FormItem>
          <FormItem>
            <Button type="success" @click="openExport()">
              <Icon type="md-cloud-download" />导出数据
            </Button>
          </FormItem>
          <!-- <FormItem>
            <Button type="primary" @click="add">
              <Icon type="md-add" />添加记录
            </Button>
          </FormItem> -->
        </div>
      </div>
      <div class="page-content" v-if="enableProSearch">
        <div>
          <FormItem label="所属分院：" :label-width="110">
            <Select v-model="searchData.branch" style="width:200px">
              <Option
                v-for="item in branchList"
                :value="item.value"
                :key="item.value"
              >{{ item.label }}</Option>
            </Select>
          </FormItem>
          <FormItem label="项目组：" :label-width="110">
            <Select v-model="searchData.projectGroup" style="width:200px">
              <Option
                v-for="item in projectList"
                :value="item.value"
                :key="item.value"
              >{{ item.label }}</Option>
            </Select>
          </FormItem>

          <FormItem class="right">
            <Button type="primary" @click="search">搜索</Button>
          </FormItem>
          <FormItem class="right">
            <Button @click="cancel">取消</Button>
          </FormItem>
        </div>
      </div>
    </Form>
    <div class="page-content">
      <t-table ref="table" :url="url.list" :param="data.param" :columns="data.columns" />
    </div>

    <d-detail ref="detail" :id="detail.id" :title="'统计数据'" :getUrl="url.get" :label="exportLabel" :keyName="exportKey" />
    <!-- <partnerinfo-edit ref="edit" :id="edit.id" /> -->
    <!-- <partnerinfo-add ref="add" /> -->
    <Modal v-model="importShow" title="Excel导入数据" :mask-closable="false" width="700">
      <Row>
        <Upload
          :action="url.importExcel"
          accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
          :show-upload-list="false"
          :before-upload="beforeUpload"
          :on-success="uploadSuccess"
        >
          <Button icon="ios-cloud-upload-outline">导入数据</Button>
        </Upload>
      </Row>
      <Row></Row>
      <Row>
        <Button icon="ios-cloud-download-outline" @click="downloadTemplate1">统计数据表 模板下载</Button>
      </Row>
      <Spin fix v-if="importLoadingShow">
        <Icon type="ios-loading" size="18" class="demo-spin-icon-load"></Icon>
        <div>正在导入数据......</div>
      </Spin>
    </Modal>
    <Modal
      v-model="exportShow"
      title="导出数据"
      :mask-closable="false"
      ok-text="导出"
      @on-ok="exportExcel"
      width="700"
    >
      <Transfer
        :data="exportdata"
        :target-keys="exportTargetKeys"
        :render-format="render"
        :list-style="listStyle"
        @on-change="handleChange"
        :filterable="true"
      ></Transfer>
    </Modal>
  </div>
</template>
<script>
import partnerinfoDetail from "@/components/partnerinfo/partnerinfodetail"
import partnerinfoEdit from "@/components/partnerinfo/partnerinfoedit"
import partnerinfoAdd from "@/components/partnerinfo/partnerinfoadd"
export default {
  name: "partnerinfolist",
  components: {
    partnerinfoDetail,
    partnerinfoEdit,
    partnerinfoAdd
  },
  data() {
    return {
      url: {
        list: "/statChart/list",
        add: "/statChart/add",
        get: "/statChart/get",
        edit: "/statChart/edit",
        del: "/statChart/del",
        getSelectOption: "/statChart/getSelectOption",
        importExcel: baseURL+"/statChart/importExcel?userId=" + JSON.parse(localStorage.getItem("user")).id,
        exportExcel: baseURL+"/statChart/exportExcel",
        template:  baseURL+"/reworkWoker/exportTemplete",
      },
      detail:{
        id:""
      },
      edit:{
        id:""
      },
      enableProSearch:false,
      searchData: {
      },
      importShow:false,
      importLoadingShow:false,
      exportShow:false,
      exportLoadingShow:false,
      listStyle: {
          width: '250px',
          height: '600px'
      },
      exportLabel:["序号","所属分院","项目组","项目组驻地","项目组总人数","面试通过率","返岗人数","计划调动人数",
                "无线项目1勘察进度","无线项目1会审通过进度","无线项目1设计进度",
                "无线项目1平台进度","有线项目1勘察进度","有线项目1会审通过进度","有线项目1设计进度","有线项目1平台进度","口罩剩余量",
                "口罩人均可使用天","项目名称"],
      exportKey:["id","branch","project_group","city","project_usernum","interview_pass_rate","returnwork_num","plan_shfit_num",
                "wireless_pro1_survey_progress","wireless_pro1_camethrough_progress","wireless_pro1_design_progress","wireless_pro1_platform_progress",
                "wired_pro1_survey_progress","wired_pro1_camethrough_progress","wired_pro1_design_progress","wired_pro1_platform_progress",
                "mask_remain","mask_percapita","project_name"],

      exportTargetKeys:[],
      branchList: [],
      projectList:[],
      specialityList: [],
      jobClassificationList: [],
      dutyStatusList: [],
      provinceList: [],
      cityList: [],
      yesornoList: [
        { value: "", label: "全部" },
        { value: "1", label: "是" },
        { value: "0", label: "否" }
      ],
      workCompanytList:[],
      workProjectList:[],
      all:{ value: "", label: "全部" },
      editshow: false,
      addshow: false,
      data: {
        param: {},
        columns: [
          {
            title: "数据时间",
            key: "updateTime",
            minWidth:120,
            tooltip:true,
            render: function(h, params) {
              if (params.row.updateTime) {
                return h(
                  "div",
                  new Date(params.row.updateTime).Format("yyyy-MM-dd")
                );
              }
            }
          },
          {
            title: "所属分院",
            key: "branch",
            minWidth: 150,
            tooltip:true,
          },
          {
            title: "项目组",
            key: "projectGroup",
            minWidth: 180,
            tooltip:true,
          },
          {
            title: "项目组驻地",
            key: "city",
            minWidth: 100,
          },
          {
            title: "项目组总人数",
            key: "projectUsernum",
            minWidth: 100,
            tooltip:true,
          },
          {
            title: "面试通过率",
            key: "interviewPassRate",
            minWidth: 100,
            render: function(h, params) {
              if (params.row.interviewPassRate) {
                return h(
                  "div",
                  (params.row.interviewPassRate*100).toFixed(2) + "%"
                );
              }
            }
          },
          {
            title: "返岗人数",
            key: "returnworkNum",
            minWidth: 100,
            tooltip:true,
            
          },
          {
            title: "计划调动人数",
            key: "planShfitNum",
            minWidth: 100,
            tooltip:true,
            
          },
          {
            title: "无线项目1勘察进度",
            key: "wirelessPro1SurveyProgress",
            minWidth: 100,
            render: function(h, params) {
              
              if (params.row.wirelessPro1SurveyProgress) {
                return h(
                  "div",
                  (params.row.wirelessPro1SurveyProgress*100).toFixed(2) + "%"
                );
              }
            }
          },
          {
            title: "无线项目1会审通过进度",
            key: "wirelessPro1CamethroughProgress",
            minWidth: 100,
            tooltip:true,
            render: function(h, params) {
              
              if (params.row.wirelessPro1CamethroughProgress) {
                return h(
                  "div",
                  (params.row.wirelessPro1CamethroughProgress*100).toFixed(2) + "%"
                );
              }
            }
          },
          {
            title: "无线项目1设计进度",
            key: "wirelessPro1DesignProgress",
            minWidth: 100,
            tooltip:true,
            render: function(h, params) {
              
              if (params.row.wirelessPro1DesignProgress) {
                return h(
                  "div",
                  (params.row.wirelessPro1DesignProgress*100).toFixed(2) + "%"
                );
              }
            }
          },
          {
            title: "无线项目1平台进度",
            key: "wirelessPro1PlatformProgress",
            minWidth: 100,
            render: function(h, params) {
              
              if (params.row.wirelessPro1PlatformProgress) {
                return h(
                  "div",
                  (params.row.wirelessPro1PlatformProgress*100).toFixed(2) + "%"
                );
              }
            }
          
          },
          {
            title: "有线项目1勘察进度",
            key: "wiredPro1SurveyProgress",
            minWidth: 100,
            render: function(h, params) {
              
              if (params.row.wiredPro1SurveyProgress) {
                return h(
                  "div",
                  (params.row.wiredPro1SurveyProgress*100).toFixed(2) + "%"
                );
              }
            }
          },
          {
            title: "有线项目1会审通过进度",
            key: "wiredPro1CamethroughProgress",
            minWidth: 100,
            render: function(h, params) {
              
              if (params.row.wiredPro1CamethroughProgress) {
                return h(
                  "div",
                  (params.row.wiredPro1CamethroughProgress*100).toFixed(2) + "%"
                );
              }
            }
           
          },
          {
            title: "有线项目1设计进度",
            key: "wiredPro1DesignProgress",
            minWidth: 100,
            tooltip:true,
            render: function(h, params) {
              
              if (params.row.wiredPro1DesignProgress) {
                return h(
                  "div",
                  (params.row.wiredPro1DesignProgress*100).toFixed(2) + "%"
                );
              }
            }
          },
          {
            title: "有线项目1平台进度",
            key: "wiredPro1PlatformProgress",
            minWidth: 100,
            tooltip:true,
            render: function(h, params) {
              
              if (params.row.wiredPro1PlatformProgress) {
                return h(
                  "div",
                  (params.row.wiredPro1PlatformProgress*100).toFixed(2) + "%"
                );
              }
            }
          },
          {
            title: "一次性口罩（个）-项目组剩余量",
            key: "maskRemain",
            minWidth: 100,
            tooltip:true,
          },
          {
            title: "口罩人均可使用天",
            key: "maskPercapita",
            minWidth: 100,
            tooltip:true,
          },
          {
            title: "项目名称",
            key: "projectName",
            minWidth: 250,
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
                    let $this = this;
                    $this.$refs.edit.changeShow()
                    $this.edit.id = params.row.id
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
                    let $this = this;
                    $this.$refs.detail.changeShow()
                    $this.detail.id = params.row.id
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
  computed:{
    exportdata:function(){
      let exportData = [];
      for (let i = 0; i < this.exportLabel.length; i++) {
        const exportLabelValue = this.exportLabel[i];
        const exportKeyValue = this.exportKey[i];
        exportData.push({"label":exportLabelValue,"key":exportKeyValue})
      }
      return exportData;
    }
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
    },
    openImport(){
      this.importShow = !this.importShow
    },
    openExport(){
      this.exportShow = !this.exportShow
      this.exportTargetKeys = []
    },
    add(){
      let $this = this;
      $this.$refs.add.changeShow()
      $this.add.id = params.row.id
    },
    
    getOptionList(selectOptionList,selectOption){
      let $this = this;
      this.$ajax({
        method: "post",
        url: this.url.getSelectOption,
        data:{
          selectOption:selectOption
        }
      }).then(function(res) {
        let optionList = res.data.data.map((value)=>{
            return {value:value,label:value};
        })
        optionList.unshift($this.all) 
        Object.assign(selectOptionList, optionList)
      });
    },
    
    cancel(){
      this.searchData={}
      this.data.param = this.searchData;
    },
    
    beforeUpload(){
      this.importLoadingShow = true;
    },
    uploadSuccess(response, file, fileList){
      this.$refs.table.searchData();
      var errorList = response.errorList
      var errorMessage = ''
      for (const key in errorList) {
        if (errorList.hasOwnProperty(key)) {
          const element = errorList[key];
          errorMessage += "第" + element.row + "行，数据异常，" + element.value+"</br>"
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
    exportExcel(){
      if(!this.exportTargetKeys.length){
        this.$Message.error("至少选择一项导出")
        setTimeout(() => {
          this.exportShow = true;
        }, 10);
        return;
      }
      let params = this.getParams(this.searchData)
      params.set("targetCellName",this.exportTargetKeys)
      window.location.href = this.url.exportExcel+"?"+params.toString()
    },
    downloadTemplate1(){
      window.location.href = this.url.template+"?fileName=统计数据表.xlsx"
    },
    getParams(data) {
      let param = new URLSearchParams();
      for (var key in data) {
        if(!data[key]){
          continue
        }
        if(data[key] instanceof Date){
          param.append(key, data[key].getTime());
          continue;
        }
        if (
          typeof data[key] == "object" &&
          data[key] != null &&
          data[key].length === 0
        ) {
          continue;
        }
        if (data[key]) {
          param.append(key, data[key]);
        }
      }
      return param;
    },
    render (item) {
      return item.label;
    },
    
  },
  created() {

    this.getOptionList(this.branchList,"branch")
    this.getOptionList(this.projectList,"project_group")
 
  }
};
</script>
