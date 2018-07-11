<template>
  <div id="app" class='maxheigh wrapper'>
    <router-view class='router-view' />
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
html,
body {
  height: 100%;
  padding: 0;
  margin: 0;
  font-family:"Microsoft YaHei";
}
.wrapper {  
    min-height: 100%;  
    margin: 0 auto -60px; /* -60pxthe bottom margin is the negative value of the footer's height */  
}  
.maxheigh {
  height: 100%;
}

.router-view {
  height: calc(100% - 69px);
}

.footer {
  background: #495060;
  text-align: center;
  color: #fff;
}
#app > div > .head-title >.head-right > div > div.ivu-select-dropdown{
  margin-top: 10px;
}
</style>
