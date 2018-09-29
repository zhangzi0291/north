<style scoped>
.layout {
  border: 1px solid #d7dde4;
  background-color: #f0f0f0;
  position: relative;
  border-radius: 4px;
  overflow: hidden;
}
.layout-logo {
  width: 200px;
  height: 50px;
  background: #5b6270;
  background-image: url("/images/logo.jpg");
  background-size: 100% 100%;
  border-radius: 3px;
  float: left;
  position: relative;
  top: 5px;
  left: 20px;
}

.layout-nav {
  float: right;
  position: relative;
  margin: 0 auto;
  margin-right: 20px;
}

.single-page-con {
  top: 60px;
  margin: 0 auto;
  bottom: 0;
  overflow: auto;
  background-color: #f0f0f0;
  z-index: 1;
  transition: left 0.3s;
  width: 80%;
  /*min-width: 1000px;*/
  min-height: calc(100% - 60px);
}

.single-page {
  margin: 10px;
  padding: 5px;
  background: #f0f0f0;
  min-height: calc(100% - 89px);
}
.main-footer-con {
  width: 100%;
}
</style>
<template>
  <div class='layout'>
    <Menu 
      mode="horizontal" 
      :theme="'dark'" 
      active-name="1">
      <div class="layout-logo"/>
      <div class="layout-nav">
        <MenuItem name="1">
        <Icon type="home"/>
        首页
                </MenuItem>
        <MenuItem name="2">
        <Icon type="ios-paper-outline"/>
        文章
                </MenuItem>
        <!--<Submenu name="3">
                                <template slot="title">
                                    <Icon type="stats-bars"></Icon>
                                    统计分析
                                </template>
                                <MenuGroup title="使用">
                                    <MenuItem name="3-1">新增和启动</MenuItem>
                                    <MenuItem name="3-2">活跃分析</MenuItem>
                                    <MenuItem name="3-3">时段分析</MenuItem>
                                </MenuGroup>
                                <MenuGroup title="留存">
                                    <MenuItem name="3-4">用户留存</MenuItem>
                                    <MenuItem name="3-5">流失用户</MenuItem>
                                </MenuGroup>
                            </Submenu>-->
        <MenuItem name="4">
        <Icon type="settings"/>
        常用网站
                </MenuItem>
      </div>
    </Menu>
    <div class="single-page-con">
      <div class="single-page">
        <router-view/>
      </div>
    </div>
    <div class="main-footer-con">
      <Footer class="footer">@copyright</Footer>
    </div>
  </div>
</template>
<script>
export default {};
</script>
