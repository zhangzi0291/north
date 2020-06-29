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
  <Modal v-model="show" scrollable width="65">
      <Row class="title">{{title}} </Row>
      <Row>
          <Form :model="data"  inline>
            <template v-for=" (i,index) in label.length" >
            <Row  v-if="index % 2 == 0" :key="index">
                <Col span="12" >
                    <FormItem :label="label[index]" :label-width="labelwidth">
                         {{data[toHump(keyName[index])]}} 
                    </FormItem>
                </Col>
                <Col span="12" v-if="label[index+1]">
                    <FormItem :label="label[index+1]" :label-width="labelwidth">
                         {{data[toHump(keyName[index+1])]}} 
                    </FormItem>
                </Col>
                
            </Row>
            </template>
          </Form>
      </Row>
  </Modal>
</template>
<script>
export default {
  name: "detail",
  data() {
    return {
      labelwidth:120,
      data:{},
      show:false,
    }

  },
  props: {
    getUrl:{
        type:String,
        default:"",
    },
    id:{
        type:String,
        default:"",
    },
    title:{
        type:String,
        default:"",
    },
    label:{
      type: Array,
      default: []
    },
    keyName:{
      type: Array,
      default: []
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
    getNull(value){
        if(!!value){
            return value
        }
        return ""
    }
  },
  watch:{
      id(val, oldVal){
          this.getDetail()
      }
  },
  methods: {
    getDetail(id){
      let $this = this;
      this.$ajax({
        method: "post",
        url: this.getUrl,
        data:{
          id:id
        }
      }).then(function(res) {
        $this.data = res.data.data
      });
    },
    changeShow(id){
        this.show = !this.show
        this.getDetail(id)
    },
  },
  mounted() {
  }
};
</script>
