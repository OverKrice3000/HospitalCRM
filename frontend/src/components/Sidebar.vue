<template>
    <ul class="sidenav app-sidenav" :class="{open: isOpen}"> 
      <router-link 
        v-for="link in links"
        :key="link.url"
        tag="li"
        active-class="active" 
        :to="link.url" 
        :exact="link.exact">
        <a href="#" class="waves-effect waves-purple sidebar-link">{{link.title}}</a>
      </router-link>
    </ul>
</template>

<script>
export default {
    props: {isOpen: Boolean},
    data: () => ({
        links:[
            {title: 'Пациенты', url: '/', exact: true},
            {title: 'Записи', url: '/appointments'},
            {title: 'Врачи', url: '/doctors'},
            {title: 'Отделы', url: '/departments'},
            {title: 'Заказы', url: '/orders'},
            {title: 'Медикаменты', url: '/medicine'},
            {title: 'Статистика', url: '/statistic'},
        ]
    })
}
</script>

<style lang="scss">

</style>