<style scoped lang="less">
/deep/.title{
    font-size: 20px;
    font-weight: 1000px;
}
/deep/.ivu-form-item-content{
    word-wrap:break-word;  
    word-break:break-all;
}
</style>
<template>
  <Modal v-model="show" scrollable width="65" @on-ok='ok' >
      <Row class="title">新增人员复工信息</Row>
      <Row>
          <Form :model="data"  inline>
            <Row>
                <Col span="12">
                    <FormItem label="姓名：" :label-width="labelwidth">
                        <Input v-model="data.workName" placeholder="" clearable/>
                    </FormItem>
                </Col>
                <Col span="12">
                    <FormItem label="合作公司：" :label-width="labelwidth">
                        <Input v-model="data.workCompany" placeholder="" clearable/>
                    </FormItem>
                </Col>
            </Row>
            
            <Row>
                <Col span="12">
                    <FormItem label="所在项目：" :label-width="labelwidth">
                        <Input v-model="data.workProject" placeholder="" clearable/>
                    </FormItem>
                </Col>
                <Col span="12">
                    <FormItem label="所在部门：" :label-width="labelwidth">
                        <Input v-model="data.departmentName" placeholder="" clearable/>
                    </FormItem>
                </Col>
            </Row>
            
            <Row>
                <Col span="12">
                    <FormItem label="工作城市：" :label-width="labelwidth">
                        <Input v-model="data.workCity" placeholder="" clearable/>
                    </FormItem>
                </Col>
                <Col span="12">
                    <FormItem label="所在城市：" :label-width="labelwidth">
                        <Input v-model="data.localCity" placeholder="" clearable/>
                    </FormItem>
                </Col>
            </Row>

            <Row>
                <Col span="12">
                    <FormItem label="体温：" :label-width="labelwidth">
                        <Input v-model="data.bodyTemperature" placeholder="" clearable/>
                    </FormItem>
                </Col>
                <Col span="12">
                    <FormItem label="是否可以现场上班：" :label-width="labelwidth">
                       <i-switch v-model="data.canGoWork" :true-value="1" :false-value="0" ></i-switch>
                    </FormItem>
                </Col>
            </Row>

            <Row>
                <Col span="24">
                    <FormItem label="预计现场上班时间：" :label-width="labelwidth">
                        <Input v-model="data.expectWorkTime" placeholder="" clearable/>
                    </FormItem>
                </Col>
            </Row>

            <Row>
                <Col span="12">
                    <FormItem label="是否省内人员：" :label-width="labelwidth">
                         <i-switch v-model="data.inProvincePerson" :true-value="1" :false-value="0" ></i-switch>
                    </FormItem>
                </Col>
                <Col span="12">
                    <FormItem label="是否返回驻地：" :label-width="labelwidth">
                         <i-switch v-model="data.returnWorkStation" :true-value="1" :false-value="0" ></i-switch>
                    </FormItem>
                </Col>
            </Row>

            <Row>
                <Col span="12">
                    <FormItem label="是否应急响应人员：" :label-width="labelwidth">
                         <i-switch v-model="data.emergencyPerson" :true-value="1" :false-value="0" ></i-switch>

                    </FormItem>
                </Col>
                <Col span="12">
                    <FormItem label="是否远程上班：" :label-width="labelwidth">
                        <i-switch v-model="data.remoteWork" :true-value="1" :false-value="0" ></i-switch>

                    </FormItem>
                </Col>
            </Row>

            <Row>
                <Col span="12">
                    <FormItem label="近期去过医院或与病源有过接触？" :label-width="labelwidth">
                         <i-switch v-model="data.hospitalOrPathogenContact" :true-value="'是'" :false-value="'否'"></i-switch>
                    </FormItem>
                </Col>
                <Col span="12">
                    <FormItem label="近期与湖北、温州亲朋接触？" :label-width="labelwidth">
                         <i-switch v-model="data.goHbOrWzFriend" :true-value="'是'" :false-value="'否'"></i-switch>
                    </FormItem>
                </Col>
            </Row>

            <Row>
                <Col span="12">
                    <FormItem label="近期去过湖北、温州？" :label-width="labelwidth">
                         <i-switch v-model="data.goHbOrWz" :true-value="'是'" :false-value="'否'"></i-switch>
                    </FormItem>
                </Col>
                <Col span="12">
                    <FormItem label="近期去过湖北？" :label-width="labelwidth">
                         <i-switch v-model="data.goHb" :true-value="'是'" :false-value="'否'"></i-switch>
                    </FormItem>
                </Col>
            </Row>

            <Row>
                <Col span="12">
                    <FormItem label="近期与湖北亲朋接触？" :label-width="labelwidth">
                         <i-switch v-model="data.goHbFriend" :true-value="'是'" :false-value="'否'"></i-switch>
                        <Input v-model="data.goHbFriend" placeholder="" clearable/>
                    </FormItem>
                </Col>
                <Col span="12">
                    <FormItem label="是否重点关注：" :label-width="labelwidth">
                         <i-switch v-model="data.focusOn" :true-value="1" :false-value="0"></i-switch>
                        <Input v-model="data.focusOn" placeholder="" clearable/>
                    </FormItem>
                </Col>
            </Row>

            <Row>
                <Col span="12">
                    <FormItem label="返回工作地日期：" :label-width="labelwidth">
                        <Input v-model="data.returnDate" placeholder="" clearable/>
                    </FormItem>
                </Col>
                <Col span="12">
                    <FormItem label="春节后可到岗日期：" :label-width="labelwidth">
                        <Input v-model="data.arrivalTime" placeholder="" clearable/>
                    </FormItem>
                </Col>
            </Row>

            <Row>
                <Col span="12">
                    <FormItem label="隔离期末：" :label-width="labelwidth">
                        <Input v-model="data.quarantineEndTime" placeholder="" clearable/>
                    </FormItem>
                </Col>
                <Col span="12">
                    <FormItem label="次日生产任务（主要一项）：" :label-width="labelwidth">
                        <Input v-model="data.productionTasks" placeholder="" clearable/>
                    </FormItem>
                </Col>
            </Row>
            
            <Row>
                <Col span="12">
                    <FormItem label="目的地：" :label-width="labelwidth">
                        <Input v-model="data.destination" placeholder="" clearable/>
                    </FormItem>
                </Col>
                <Col span="12">
                    <FormItem label="需求分院短租或长租车：" :label-width="labelwidth">
                         <i-switch v-model="data.needCar" :true-value="'是'" :false-value="'否'"></i-switch>
                    </FormItem>
                </Col>
            </Row>
            
             <Row>
                <Col span="12">
                    <FormItem label="从当前所在地返程到工作所在地交通工具：" :label-width="labelwidth">
                        <Input v-model="data.returnVehicle" placeholder="" clearable/>
                    </FormItem>
                </Col>
                <Col span="12">
                    <FormItem label="班次信息：" :label-width="labelwidth">
                        <Input v-model="data.shiftInfo" placeholder="" clearable/>
                    </FormItem>
                </Col>
            </Row>

             <Row>
                <Col span="12">
                    <FormItem label="班次时间：" :label-width="labelwidth">
                        <Input v-model="data.shiftTime" placeholder="" clearable/>
                    </FormItem>
                </Col>
                <Col span="12">
                    <FormItem label="驻点区县：" :label-width="labelwidth">
                        <Input v-model="data.stationedCounty" placeholder="" clearable/>
                    </FormItem>
                </Col>
            </Row>

             <Row>
                <Col span="12">
                    <FormItem label="班组长：" :label-width="labelwidth">
                       <Input v-model="data.classMonitor" placeholder="" clearable/>
                    </FormItem>
                </Col>
                <Col span="12">
                    <FormItem label="是否外出勘察：" :label-width="labelwidth">
                        <i-switch v-model="data.outSurvey" :true-value="'是'" :false-value="'否'"></i-switch>
                        <Input v-model="data.outSurvey" placeholder="" clearable/>
                    </FormItem>
                </Col>
            </Row>

             <Row>
                <Col span="12">
                    <FormItem label="勘察地点：" :label-width="labelwidth">
                        <Input v-model="data.surveyPlace" placeholder="" clearable/>
                    </FormItem>
                </Col>
                <Col span="12">
                    <FormItem label="勘察日期：" :label-width="labelwidth">
                        <Input v-model="data.surveyDate" placeholder="" clearable/>
                    </FormItem>
                </Col>
            </Row>

             <Row>
                <Col span="12">
                    <FormItem label="出行方式：" :label-width="labelwidth">
                        <Input v-model="data.tripMode" placeholder="" clearable/>
                    </FormItem>
                </Col>
                <Col span="12">
                    <FormItem label="勘察需求人：" :label-width="labelwidth">
                        <Input v-model="data.surveyNeedder" placeholder="" clearable/>
                    </FormItem>
                </Col>
            </Row>

             <Row>
                <Col span="12">
                    <FormItem label="勘察审批人：" :label-width="labelwidth">
                        <Input v-model="data.surveyApprover" placeholder="" clearable/>
                    </FormItem>
                </Col>
                <Col span="12">
                    <FormItem label="其他补充说明：" :label-width="labelwidth">
                        <Input v-model="data.otherRemark" placeholder="" clearable/>
                    </FormItem>
                </Col>
            </Row>

            <Row>
                <Col span="12">
                    <FormItem label="统计人：" :label-width="labelwidth">
                        <Input v-model="data.statisticians" placeholder="" clearable/>
                    </FormItem>
                </Col>
                <Col span="12">
                    <FormItem label="本小区或临近小区是否有确诊病例：" :label-width="labelwidth">
                        <Input v-model="data.neighborhoodCellInfo" placeholder="" clearable/>
                    </FormItem>
                </Col>
            </Row>
            
            <Row>
                <Col span="12">
                    <FormItem label="确诊小区：" :label-width="labelwidth">
                        <Input v-model="data.corroborateCell" placeholder="" clearable/>
                    </FormItem>
                </Col>
                <Col span="12">
                    <FormItem label="远程或现场：" :label-width="labelwidth">
                        <Input v-model="data.remoteOrScene" placeholder="" clearable/>
                    </FormItem>
                </Col>
            </Row>
            
            <Row>
                <Col span="12">
                    <FormItem label="具体从事的工作（XX专业代维、高中低优化服务、家客装维等，XX服务)：" :label-width="labelwidth">
                        <Input v-model="data.specificWork" placeholder="" clearable/>
                    </FormItem>
                </Col>
                <Col span="12">
                    <FormItem label="事由（“三种情况”人员（离开湖北或者有过湖北地区人员：接触史、生病发热、与患有新型冠状病毒感染肺炎的病人接触史等情况）：" :label-width="labelwidth">
                        <Input v-model="data.reason" placeholder="" clearable/>
                    </FormItem>
                </Col>
            </Row>

            <Row>
                <Col span="12">
                    <FormItem label="往来武汉或者与来自武汉的人有接触具体情况：" :label-width="labelwidth">
                        <Input v-model="data.contactSituation" placeholder="" clearable/>
                    </FormItem>
                </Col>
            </Row>
            

          </Form>
      </Row>
  </Modal>
</template>
<script>
export default {
  name: "reworkadd",
  data() {
    return {
      url: {
        list: "/reworkWoker/list",
        add: "/reworkWoker/add",
        get: "/reworkWoker/get",
        edit: "/reworkWoker/edit",
        del: "/reworkWoker/del",
        getSelectOption: "/reworkWoker/getSelectOption"
      },
      labelwidth:120,
      data:{
          workName:""
      },
      show:false,
    }

  },
  props: {
    id:{
      type: String,
      default: ""
    }
  },
  filters:{
    getYesOrNo(a){
      if(a==0){
          return "否"
      }else{
          return "是"
      }
    },
  },
  watch:{
      
  },
  methods: {
    ok: function() {
      let $this = this;
    //   if (this.$refs.editModal.validateForm()) {
    //     $this.addshow = false;
    //     setTimeout(function() {
    //       $this.addshow = true;
    //     }, 1);
    //     return;
    //   }
      this.$ajax({
        method: "post",
        url: this.url.add,
        data: this.data
      }).then(function(res) {
        $this.successModal("新增成功");
        $this.$parent.$refs.table.searchData();
      });
    },
    changeShow(){
        this.show = !this.show
    },
    change(value){
        console.log(this.data.canGoWork)
    }
  },
  mounted() {
  }
};
</script>
