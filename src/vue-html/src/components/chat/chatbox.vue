<template>
 <Scroll 
      ref="talkbox" 
      class="talk-box" 
      :height="chatBoxHeight" 
      loading-text="加载中" 
      :on-reach-top="topLoad">
    <Row type="flex" align="top">
    <template v-for="item in content">
        <template v-if="item.chatFrom == user.uid">
        <Row class-name="talk-content right">
            <div class="talk-title right">{{ item.chatFrom }} {{ item.updateTime }}</div>
            <div 
            class="talk-message my-talk-color right" 
            v-html="item.chatMsg"/>
        </Row>
        <Row class-name="talk-head">
            <img :src='item.imgSrc?item.imgSrc:"/images/unknow.jpg"'>
        </Row>
        </template>
        <template v-else>
        <Row class-name="talk-head">
            <img :src='item.imgSrc?item.imgSrc:"/images/unknow.jpg"'>
        </Row>
        <Row class-name="talk-content">
            <div class="talk-title">{{ item.chatFrom }} {{ item.updateTime }}</div>
            <div 
            class="talk-message talk-color" 
            v-html="item.chatMsg"/>
        </Row>
        </template>
    </template>
    </Row>
 </Scroll>
</template>
<script>
export default {
  name: "ChatBox",
  props: {
    chatBoxHeight: {
      type: Number,
      default: 600
    },
    content: {
      type: Array,
      default: []
    },
    user: {
      type: Object,
      default: {}
    }
  },
  watch: {
    content: {
      handler(newName, oldName) {
        this.scrollToBottom()
      },
      deep: true
    }
  },
  methods: {
    topLoad: function() {
      return new Promise(resolve => {
        setTimeout(function(params) {
          resolve();
        }, 3000);
      });
    },
    //翻页到最后
    scrollToBottom: function() {
      setTimeout(function() {
        document.getElementsByClassName(
          "ivu-scroll-container"
        )[0].scrollTop = document.getElementsByClassName(
          "ivu-scroll-container"
        )[0].scrollHeight;
      }, 50);
    }
  }
};
</script>
<style lang="less" scoped>
.right {
  text-align: right;
}
.talk-box {
  margin: 0px 10px ;
  li {
    list-style: none;
  }
  .talk-head {
    width: 3rem;
    text-align: center;
    img {
      margin-top: 10px;
      width: 40px;
      height: 40px;
      border-radius: 40px;
    }
  }
  .talk-content {
    width: calc(100% - 4rem);
    word-wrap: break-word;
    word-break: break-all;

    .talk-title {
      font-size: 1rem;
    }
    .talk-message {
      font-size: 1rem;
      padding: 5px 10px 5px;
      border-radius: 10px;
      display: inline-block;
    }
    .talk-color {
      background-color: #e0e0e0;
    }
    .my-talk-color {
      background-color: #66ccff;
    }
  }
}
</style>
