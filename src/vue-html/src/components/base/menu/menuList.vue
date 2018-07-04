<style scoped>
/*.layout-menu {
    height: 100%;
    width: 100%;
}*/
</style>

<template>
    <!--<Menu ref="sideMenu" :active-name="$route.name" :open-names="openNames" :theme="menuTheme" width="auto" @on-select="changeMenu">-->
    <div>
        <template v-for="item in menuList">
            <MenuItem v-if="item.child && item.child.length==0" :name="item.resourceUrl">
                <Icon :color="iconColor" :type="item.resourceIcon" :size="iconSize"></Icon>
                <span class="layout-text">{{item.resourceName}}</span>
            </MenuItem>
            <Submenu v-else :name="item.resourceUrl">
                <!--<template v-for="child in item">
                    <MenuItem v-if="!child.child " :name="item.resourceUrl">
                        <Icon :color="iconColor" :type="item.resourceIcon" :size="iconSize"></Icon>
                        <span class="layout-text">{{item.resourceName}}</span>
                    </MenuItem>-->
                    <template slot="title">
                        <Icon :color="iconColor" :type="item.resourceIcon" :size="iconSize"></Icon>
                        <span class="layout-text">{{item.resourceName}}</span>
                    </template>
                    <menu-list  :menu-list="item.child" :theme="menuTheme" width="auto" @on-select="changeMenu"></menu-list>
                <!--</template>-->
            </Submenu>
        </template>
    </div>
    <!--</Menu>-->
</template>

<script>
export default {
    name: 'menuList',
    props: {
        menuList: Array,
        iconSize: Number,
        iconColor: {
            type: String,
            default: 'white'
        },
        menuTheme: {
            type: String,
            default: 'dark'
        },
        openNames: {
            type: Array
        }
    },
    // props: ['menuList','iconSize','menuTheme','openNames'],
    methods: {
        changeMenu(active) {
            this.$emit('on-change', active);
        },
        itemTitle(item) {
            if (typeof item.title === 'object') {
                return this.$t(item.title.i18n);
            } else {
                return item.title;
            }
        }
    },
    updated() {
        this.$nextTick(() => {
            if (this.$refs.sideMenu) {
                this.$refs.sideMenu.updateOpened();
            }
        });
    }

};
</script>
