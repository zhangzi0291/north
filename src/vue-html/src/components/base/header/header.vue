<style scoped>
</style>
<template>
  <div class="main-header">
    <div class="navicon-con">
      <Button 
        :style="{transform: 'rotateZ(' + (this.shrink ? '-90' : '0') + 'deg)'}" 
        type="text" 
        @click="toggleClick">
        <Icon 
          type="md-menu"
          size="30"/>
      </Button>
    </div>
    <div class="header-avator-con">
      <div class="user-dropdown-menu-con">
        <Row 
          type="flex" 
          justify="end" 
          align="middle" 
          class="user-dropdown-innercon">
          <Dropdown 
            transfer 
            trigger="click" 
            @on-click="handleClickUserDropdown">
            <a href="javascript:void(0)">
              <span class="main-user-name">{{ $store.state.user.name }}</span>
              <Icon type="md-arrow-dropdown"/>
            </a>
            <DropdownMenu slot="list">
              <DropdownItem 
                name="loginout" 
                divided>退出登录</DropdownItem>
            </DropdownMenu>
          </Dropdown>
          <Avatar 
            icon="md-person" 
            style="background: #619fe7;margin-left:10px;"/>
        </Row>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: "Mheader",
  methods: {
    toggleClick: function() {
      this.$emit("toggleClick");
    },
    handleClickUserDropdown(name) {
      let $this = this;
      this.$ajax({
        method: "POST",
        url: "/sys/logout"
      }).then(function(res) {
        $this.$store.commit("LOGOUT");
        $this.$router.push({
          name: "login"
        });
      });
    }
  },
  props: {
    shrink: {
      type: Boolean,
      default: false
    }
  }
};
</script>
