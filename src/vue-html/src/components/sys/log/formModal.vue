<style scoped lang="less">
/deep/.title{
    font-size: 20px;
    font-weight: 900;
}
/deep/.ivu-form-item-content{
    word-wrap:break-word;  
    word-break:break-all;
}
</style>
<template>
  <Modal v-model="show" scrollable width="65" @on-ok='ok' >
      <Row class="title">新增{{title}}</Row>
      <Row>
          <Form :model="data"  inline>
            <Row>
                <Col span="12">
                    <FormItem label="操作时间：" :label-width="labelwidth" prop="createTime">
                        <Input v-model="data.createTime" placeholder="" clearable/>
                    </FormItem>
                </Col>
                <Col span="12">
                    <FormItem label="IP：" :label-width="labelwidth" prop="ip">
                        <Input v-model="data.ip" placeholder="" clearable/>
                    </FormItem>
                </Col>
            </Row>
            <Row>
                <Col span="12">
                    <FormItem label="模块名称：" :label-width="labelwidth" prop="moduleName">
                        <Input v-model="data.moduleName" placeholder="" clearable/>
                    </FormItem>
                </Col>
                <Col span="12">
                    <FormItem label="操作名称：" :label-width="labelwidth" prop="operationName">
                        <Input v-model="data.operationName" placeholder="" clearable/>
                    </FormItem>
                </Col>
            </Row>
            <Row>
                <Col span="12">
                    <FormItem label="操作用户：" :label-width="labelwidth" prop="username">
                        <Input v-model="data.username" placeholder="" clearable/>
                    </FormItem>
                </Col>
                <Col span="12">
                    <FormItem label="备注：" :label-width="labelwidth" prop="remark">
                        <Input v-model="data.remark" placeholder="" clearable/>
                    </FormItem>
                </Col>
            </Row>

          </Form>
      </Row>
      <div slot="footer">
          <Button @click="show=false">取消</Button>
          <Button type="primary" @click="ok()">确定</Button>
      </div>
  </Modal>
</template>
<script>
export default {
  name: "sysLogForm",
  data() {
    return {
      url:"",
      labelwidth:120,
      id:"",
      data:{},
      show:false,
    }

  },
  props: {
    //标题
    title:{
      type: String,
      default: ""
    },
    //查询单条记录接口的url
    getUrl:{
      type: String,
      default: ""
    },
    //新增接口的url
    addUrl:{
      type: String,
      default: ""
    },
    //编辑接口的url
    editUrl:{
      type: String,
      default: ""
    },
    okCallback:{
        type: Function,
        default: function(){
            
        }
    },
  },
  filters:{
    
  },
  watch:{
      
  },
  methods: {
    //提交
    ok: function() {
      let $this = this;
      // let flag = false;
      // //校验
      // this.$refs.form.validate(valid => {
      //     flag = valid
      // });
      // if(!flag){
      //     this.show = true
      //     return
      // }
      this.$ajax({
        method: "post",
        url: this.url,
        data: this.data
      }).then(function(res) {
        $this.successModal("操作成功");
        $this.okCallback()
        //刷新表格
        $this.$parent.$refs.table.searchData();
        this.show = false
      });
    },
    //弹出或关闭模态窗
    changeShow(id){
        this.id = id;
        console.log(this.id)
        this.show = true
        if(!!this.id){
            this.$ajax({
                method: "post",
                url: this.url.get,
                data: {
                    id:this.id
                }
            }).then(res => {
                this.data = res.data.data
            });
            this.url = this.editUrl
        }else{
            this.url = this.addUrl
            this.data = {}
        }

    },
  },
  mounted() {
  }
};
</script>
