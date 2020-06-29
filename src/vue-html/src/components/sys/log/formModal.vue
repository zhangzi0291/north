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
      <Row class="title">新增{{title}}</Row>
      <Row>
          <Form :model="data"  inline>
            <Row>
                <Col span="12">
                    <FormItem label="操作时间：" :label-width="labelwidth">
                        <Input v-model="data.createTime" placeholder="" clearable/>
                    </FormItem>
                </Col>
                <Col span="12">
                    <FormItem label="IP：" :label-width="labelwidth">
                        <Input v-model="data.ip" placeholder="" clearable/>
                    </FormItem>
                </Col>
            </Row>
            <Row>
                <Col span="12">
                    <FormItem label="模块名称：" :label-width="labelwidth">
                        <Input v-model="data.moduleName" placeholder="" clearable/>
                    </FormItem>
                </Col>
                <Col span="12">
                    <FormItem label="操作名称：" :label-width="labelwidth">
                        <Input v-model="data.operationName" placeholder="" clearable/>
                    </FormItem>
                </Col>
            </Row>
            <Row>
                <Col span="12">
                    <FormItem label="操作用户：" :label-width="labelwidth">
                        <Input v-model="data.username" placeholder="" clearable/>
                    </FormItem>
                </Col>
                <Col span="12">
                    <FormItem label="备注：" :label-width="labelwidth">
                        <Input v-model="data.remark" placeholder="" clearable/>
                    </FormItem>
                </Col>
            </Row>

          </Form>
      </Row>
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
    title:{
      type: String,
      default: ""
    },
    getUrl:{
      type: String,
      default: ""
    },
    addUrl:{
      type: String,
      default: ""
    },
    editUrl:{
      type: String,
      default: ""
    },
  },
  filters:{
    
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
        url: this.url,
        data: this.data
      }).then(function(res) {
        $this.successModal("操作成功");
        $this.$parent.$refs.table.searchData();
      });
    },
    changeShow(id){
        this.id = id;
        console.log(this.id)
        this.show = !this.show
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
