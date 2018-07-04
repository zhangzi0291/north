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
/*#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}*/

html,
body {
  height: 100%;
  padding: 0;
  margin: 0;
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
</style>
