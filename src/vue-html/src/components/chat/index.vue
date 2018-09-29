<template>
  <div>
    <Row 
      type="flex" 
      class-name="head-title" 
      justify="space-around" 
      align="middle" 
      style="background:#00a2ea">
      <Col 
        span="4" 
        class-name="head-left">
      <slot name="right-button"/>
      </Col>
      <Col 
        span="16" 
        class-name="head-center">
      <p>{{ title }}</p>
      </Col>
      <Col 
        span="4" 
        class-name="head-right" >
      <slot name="left-button"/>
      </Col>
    </Row>
  </div>
</template>
<script>
// let ws = new WebSocket("ws://localhost:8000/springboot/wchat");

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
.head-title {
  height: 3rem;
  background: "#00a2ea";
  text-align: center;
  .head-center {
    font-size: 1.1rem;
    color: #fff;
    font-weight: 600;
  }
  .head-right {
    .head-setting {
      cursor: pointer;
    }
  }
}
</style>
