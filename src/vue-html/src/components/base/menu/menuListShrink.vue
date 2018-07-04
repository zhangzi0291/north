<template>
    <div>
        <template v-for="(item, index) in menuList">
            <div style="text-align: center;" >
                <Dropdown transfer v-if="item.child&&item.child.length ==0 " placement="right-start"  @on-click="changeMenu">
                    <Button @click="changeMenu(item.resourceUrl)" style="width: 70px;margin-left: -5px;padding:10px 0;" type="text">
                        <Icon :size="20" :color="iconColor" :type="item.resourceIcon"></Icon>
                    </Button>
                    <DropdownMenu style="width: 200px;" slot="list">
                        <DropdownItem :name="item.resourceUrl">
                            <Icon :type="item.resourceIcon"></Icon>
                            <span style="padding-left:10px;">{{ item.resourceName }}</span>
                        </DropdownItem>
                    </DropdownMenu>
                </Dropdown>
                <Dropdown transfer v-else  placement="right-start"  @on-click="changeMenu">
                    <Button style="width: 70px;margin-left: -5px;padding:10px 0;" type="text">
                        <Icon :size="20" :color="iconColor" :type="item.resourceIcon"></Icon>
                    </Button>
                    <DropdownMenu style="width: 200px;" slot="list">
                        <template v-for="(child, i) in item.child">
                            <DropdownItem :name="child.resourceUrl">
                                <Icon :type="child.resourceIcon"></Icon>
                                <span style="padding-left:10px;">{{ child.resourceName }}</span>
                            </DropdownItem>
                        </template>
                    </DropdownMenu>
                </Dropdown>
            </div>
        </template>
    </div>
</template>

<script>
export default {
    name: 'menuListShrink',
    props: {
        menuList: {
            type: Array
        },
        iconSize: Number,
        iconColor: {
            type: String,
            default: 'white'
        },
        menuTheme: {
            type: String,
            default: 'darck'
        }
    },
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
    }
};
</script>
<style scoped>
/*.right-button{
    width: 70px;
    margin-left: -5px;
    padding:10px 0;
}*/
</style>
