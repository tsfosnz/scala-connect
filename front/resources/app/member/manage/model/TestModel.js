var vm = {
    populate: function () {
        // prepare the data for prototype

        var project = this.project.populate();
        var teamA = this.team.populate();
        var teamB = this.team.populate();

        teamA.members.forEach(function (value) {
            project.members.push(value);
        });

        for (var i = 0; i < 1; ++i) {
            var schedule = this.schedule.populate(project.project_id);
            for (var j = 0; j < 1; ++j) {
                var item = this.action.populate(schedule.schedule_id);
                item.members.push(teamA.members[0]);
                item.members.push(teamA.members[1]);
                console.log(item.members);
                schedule.actions.push(item);
            }
        }

        console.log("populate");
        console.log(this.project.list);
        console.log(this.team.list);

    },

    project: {
        id: 1,
        schema: {
            project_id: 0,
            manager_id: 0,
            name: "",
            description: "",
            status: "",
            is_archived: false,
            started_at: "",
            finished_at: "",
            archived_at: "",
            members: [],
        },
        list: [],
        all: function () {
            return this.list;
        },
        add: function () {

        },
        populate: function () {

            var item = _.cloneDeep(this.schema);
            item.project_id = this.id++;

            item.name = faker.commerce.productName();
            item.description = faker.commerce.productName();
            item.status = "plan";

            this.list.push(item);

            return item;
        }
    },

    team: {
        id: 1,
        schema: {
            team_id: 0,
            name: "",
            description: "",
            members: []
        },
        list: [],
        all: function () {
            return this.list;
        },
        populate: function () {

            var item = _.cloneDeep(this.schema);
            item.team_id = this.id++;
            //console.log(faker);
            item.name = faker.commerce.productName();
            item.description = faker.commerce.productName();

            for (var i = 0; i < 5; ++i) {
                var member = vm.member.populate();
                item.members.push(member);
            }

            this.list.push(item);

            return item;

        }
    },

    member: {
        id: 1,
        schema: {
            member_id: 0,
            username: "",
            email: "",
            description: ""
        },
        list: [],
        populate: function () {

            var item = _.clone(this.schema);
            item.member_id = this.id++;
            //console.log(faker);
            item.name = faker.name.findName();
            item.email = faker.internet.email();
            item.description = faker.commerce.productName();
            this.list.push(item);

            return item;

        }

    },

    schedule: {
        id: 1,
        schema: {
            schedule_id: 0,
            project_id: 0,
            name: "",
            description: "",
            started_at: "",
            finished_at: "",
            actions: [],
            members: []
        },
        list: [],
        all: function () {
            return this.list;
        },
        populate: function (projectId) {

            var item = _.clone(this.schema);

            item.schedule_id = this.id++;
            item.project_id = projectId;

            item.name = faker.name.findName();
            item.description = faker.commerce.productName();
            this.list.push(item);

            return item;
        }
    },

    action: {
        id: 1,
        schema: {
            action_id: 0,
            schedule_id: 0,
            action_status_id: 0,
            name: "",
            description: "",
            started_at: "",
            finished_at: "",
            checklist: [],
            issues: [],
            members: []
        },
        list: [],
        populate: function (scheduleId) {

            var item = _.clone(this.schema);

            item.action_id = this.id++;
            item.schedule_id = scheduleId;

            item.name = faker.name.findName();
            item.description = faker.commerce.productName();

            var date = new Date();
            var year = date.getFullYear();
            var month = date.getMonth() + 1;
            var day = date.getDay() + 1;

            item.started_at = year + "-" + month + "-" + day;
            item.finished_at = year + "-" + month + "-" + (day + parseInt((Math.random() * 100 / 100)));

            this.list.push(item);

            return item;
        }
    },

    actionIssue: {},

    issueComment: {}

};

module.exports = vm;

var _project = {
    project_id: 0,
    manager_id: 0,
    name: "",
    description: "",
    started_at: "",
    finished_at: "",
    teams: []
};

var team = {
    team_id: 0,
    name: "",
    description: "",
    members: []
};

var member = {
    member_id: 0,
    username: "",
    email: "",
    description: ""
};

// a project could have many schedules
// and a schedule could have one or more actions
// and an action could have one or many members (from the same team)

var schedule = {
    schedule_id: 0,
    project_id: 0,
    name: "",
    description: "",
    started_at: "",
    finished_at: "",
    actions: [],
    members: [] // decided by action?
};

var action = {
    action_id: 0,
    schedule_id: 0,
    action_status_id: 0,
    name: "",
    description: "",
    started_at: "",
    finished_at: "",
    checklist: [],
    issues: [],
    members: []
};

var action_checklist = {
    action_checklist_id: 0,
    action_id: 0,
    name: "",
    value: false
};

var action_issue = {
    action_issue_id: 0,
    action_id: 0,
    description: "",
    comments: []
};

var action_issue_comment = {
    action_issue_comment_id: 0,
    action_issue_id: 0,
    description: ""
};

function initialize() {
    // create one project

    // create one team

    // create many members

    // join the team

    // join the project
}

function addSchedule() {

}

function addAction() {

}

