<style scoped>
.layout-menu {
    height: 100%;
    width: 100%;
}
</style>

<template>
    <div :style="{background: bgColor}" class="layout-menu">
        <slot name="top"></slot>
         <Menu v-show="!shrink" ref="sideMenu" :active-name="$route.name" :open-names="openNames" :theme="theme" width="auto" @on-select="changeMenu">
            <menu-list v-show="!shrink" :iconSize="20" :menu-theme="theme" :menu-list="menuList" :open-names="openNames" @on-change="changeMenu"></menu-list>
         </Menu>
        <menu-list-shrink v-show="shrink" :iconSize="20" :menu-theme="theme" :menu-list="menuList" :icon-color="shrinkIconColor" @on-change="changeMenu"></menu-list-shrink>
    </div>
</template>
<script>
import menuList from '@/components/base/menu/menuList';
import menuListShrink from '@/components/base/menu/menuListShrink';
export default {
    name: 'MMenu',
    components: {
        menuList,
        menuListShrink
    },
    data () {
        return {
            menuList:[]
        }
    },
    props: {
        shrink: {
            type: Boolean,
            default: false
        },
        theme: {
            type: String,
            default: 'dark'
        },
        beforePush: {
            type: Function
        },
        openNames: {
            type: Array
        }
    },
    computed: {
        bgColor() {
            return this.theme === 'dark' ? '#495060' : '#fff';
        },
        shrinkIconColor() {
            return this.theme === 'dark' ? '#fff' : '#495060';
        }
    },
    methods: {
        changeMenu(name) {
            this.$router.push(name)
        }
    },
    beforeMount() {
        let $this = this
        this.$ajax({
            method: 'post',
            url: '/sys/getMenu',
            data:{
                id:$this.$store.state.user.id
            }
        }).then(function(res) {
            $this.menuList = res.data.menu
        })
    }
};
</script>

