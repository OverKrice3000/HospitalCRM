<template>
  <div class="app-main-layout">
    <Navbar @clickSidebar = "isSideBarOpen = !isSideBarOpen"/>
    <Sidebar :isOpen="isSideBarOpen"/>

      <main class="app-content" :class="{full: !isSideBarOpen}">
          <div class="app-page">
              <router-view/>
          </div>
      </main>
  </div>
</template>

<script>
import Navbar from '@/components/Navbar'
import Sidebar from '@/components/Sidebar'
export default {
  name: 'main-layout',
  data: () => ({
    isSideBarOpen: true,
  }),
  components: {
    Navbar, Sidebar
  }
}
</script>