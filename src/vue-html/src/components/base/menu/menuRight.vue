<style scoped>
.layout-menu {
  height: 100%;
  width: 100%;
}
</style>

<template>

  <Dropdown>
    <DropdownMenu 
      transfer 
      style="width: 200px;" 
      placement="right-start" 
      slot="list">
      <template v-for="item in menuList">
        <DropdownItem :name="item.resourceUrl" >
          <Icon 
            :size="iconSize" 
            :color="iconColor" 
            :type="item.resourceIcon"/>
          <span style="padding-left:10px;">{{ item.resourceName }}</span>
        </DropdownItem>
        <Dropdown 
          placement="right-start" 
          v-if="item.child&&item.child.length > 0 && item.resourceName==undefined " 
          style="width: 200px;">
          <menu-right 
            :icon-size="iconSize" 
            :menu-theme="menuTheme" 
            :menu-list="item.child" 
            :icon-color="iconColor" 
            @on-change="changeMenu"/>
        </Dropdown>
      </template>
    </DropdownMenu>
  </Dropdown>
</template>

<script>
export default {
  name: "MenuRight",
  props: {
    menuList: {
      type: Array
    },
    iconSize: Number,
    iconColor: {
      type: String,
      default: "white"
    },
    menuTheme: {
      type: String,
      default: "darck"
    }
  },
  methods: {
    changeMenu(active) {
      this.$emit("on-change", active);
    },
    itemTitle(item) {
      if (typeof item.title === "object") {
        return this.$t(item.title.i18n);
      } else {
        return item.title;
      }
    }
  }
};
</script>
