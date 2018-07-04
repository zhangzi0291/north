<template>
  <div class="main" :class="{'main-hide-text': shrink}">
    <div class="sidebar-menu-con" :style="{width: shrink?'60px':'200px', overflow: shrink ? 'visible' : 'auto'}">
      <m-menu :shrink='shrink' :menu-list="menuList">
        <div slot="top" class="logo-con">
          <img src="../images/logo.jpg" key="max-logo" :style="shrink" />
        </div>
      </m-menu>
    </div>
    <div class="main-header-con" :style="{paddingLeft: shrink?'60px':'200px'}">
      <m-header :shrink='shrink' v-on:toggleClick='toggleClick'></m-header>
    </div>
    <div class="single-page-con" :style="{left: shrink?'60px':'200px'}">
      <div class="single-page">
        <router-view></router-view>
      </div>
      <div class="main-footer-con" >
        <Footer class="footer">@copyright</Footer>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'index',
  data() {
    return {
      menuList: [],
      shrink: false,
    };
  },
  mounted () {
  },
  methods: {
    toggleClick() {
      this.shrink = !this.shrink;
    },
    
  },
  

}
</script>
<style lang="less">
@import "./main.less";
</style>
