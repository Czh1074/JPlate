import cookie from 'js-cookie'

export default (context) => {
    const filterList = ['/trend/addTrend', '/good/addGood', '/message/addMessage'];
    if (process.client) {
        context.app.router.beforeEach((to, from, next) => {
            if (filterList.indexOf(to.path) !== -1) {
                if (cookie.get('plate_token')) {
                    next();
                } else {
                    next('/login');
                }
            } else {
                next();
            }
        })
    }
};