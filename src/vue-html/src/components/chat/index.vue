<template>
    <Row 
      type="flex" 
      justify="space-around" 
      align="middle" >
        123
    </Row>
</template>
<script>
export default {
  name: "ChatHeader",
  data() {
    return {
      title: "在线聊天" //13字一行
    };
  },
  created() {
    //设置页面高度
    // const inputHeight = 65+50;
    // this.msgBoxHeight =
    //   window.document.documentElement.clientHeight - inputHeight;
    // window.onresize = () => {
    //   this.msgBoxHeight =
    //     window.document.documentElement.clientHeight - inputHeight;
    // };
  },
  mounted() {}
};
</script>
<style lang="less">

</style>
