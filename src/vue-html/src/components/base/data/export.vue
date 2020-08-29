<style scoped lang="less">
</style>
<template>
  <Modal
    v-model="show"
    title="导出Excel数据"
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
</template>
<script>
export default {
  name: "eExport",
  data() {
    return {
        show:false,
        exportLoadingLoadingShow:false,
        listStyle: {
          width: '300px',
          height: '600px'
        },
        exportTargetKeys:[],
    };
  },
  props: {
    //导出的中文
    exportLabel:{
      type: Array,
      default: ()=>{
          return []
      }
    },
    //导出的字段
    exportKey:{
      type: Array,
      default: ()=>{
          return []
      }
    },
    //标题
    title:{
        type:String,
        default:"",
    },
    //导出接口的url
    getExportExcelUrl:{
        type:String,
        default:"",
    },
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
    //打开或关闭模态窗
    changeShow(){
        this.exportTargetKeys = []
        this.show = !this.show
    },
    //调用导出
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
      console.log(this.getExportExcelUrl+"?"+params.toString())
      window.location.href = this.getExportExcelUrl+"?"+params.toString()
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
  mounted() {}
};
</script>
