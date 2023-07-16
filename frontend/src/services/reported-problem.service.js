import UrlUtil from '../util/url-util';
import {fetchWithAuthorization, getResource} from '../util/http';

const serviceHost = process.env.REACT_APP_API_ROOT_URL;
export function svcUpdateReportedProblemStatus(token, reportedProblemId, problemStatusId, subscriberId) {
    const formattedUrl = UrlUtil.constructUrl({
        path: '/reported-problems/:reportedProblemId/status',
        pathVariables: { reportedProblemId },
        queryObject: {
            problemStatusId: problemStatusId,
            subscriberId: subscriberId,
        }
    });

    const serviceUrl = serviceHost + formattedUrl;
    return fetchWithAuthorization(serviceUrl, 'PATCH', token);
}

export function svcBanUser(token, userDeviceId) {

    const formattedUrl = UrlUtil.constructUrl({
        path: '/reported-problems/users/:userDeviceId/ban',
        pathVariables: { userDeviceId }
    });

    const serviceUrl = serviceHost + formattedUrl;
    return fetchWithAuthorization(serviceUrl, 'PATCH', token);
}

export function svcGetSubscriberReportedProblemsHistory(token, subscriberId) {
    const formattedUrl = UrlUtil.constructUrl({
        path: '/reported-problems/subscriber-history',
        queryObject: {
            subscriberId: subscriberId,
        }
    });

    const serviceUrl = serviceHost + formattedUrl;
    return fetchWithAuthorization(serviceUrl, 'GET', token);
}

export function svcGetProblemsCategories() {
    const formattedUrl = UrlUtil.constructUrl({
        path: '/problems',
    });

    const serviceUrl = serviceHost + formattedUrl;
    return getResource(serviceUrl);
}

export function svcGetProblemsStatuses() {
    const formattedUrl = UrlUtil.constructUrl({
        path: '/problem-statuses',
    });

    const serviceUrl = serviceHost + formattedUrl;
    return getResource(serviceUrl);
}