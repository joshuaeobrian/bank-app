import {useContext, useEffect, useState} from 'react';
import {Button, Modal, Form, CloseButton} from 'react-bootstrap'
import {CardInfoContext} from '../CardInfo/CardInfo';
import {toast} from 'react-toastify';
import axios from 'axios';
import './ReportIssue.scss'

export const ReportIssue = () => {
    const [cards, updateCard] = useContext(CardInfoContext);
    const [show, setShow] = useState(false);

    const [cardStatuses, setCardStatuses] = useState();
    const [selectedCard, setSelectedCard] = useState();
    const [selectedIssueType, setSelectedIssueType] = useState();
    const [comment, setComment] = useState('');

    useEffect(() => {
        axios.get('/v1/card-issue/statuses').then(({data}) => setCardStatuses(data));
    }, []);

    const handleClose = () => {
        setComment(undefined);
        setSelectedIssueType(undefined);
        setSelectedCard(undefined);
        setShow(false);
    };

    const handleShow = () => setShow(true);
    const handleSelectedCardChange = (e) => {
        setSelectedCard(e.target.value);
    };
    const handleSelectedIssueChange = (e) => {
        setSelectedIssueType(e.target.value);
    };
    const handleCommentChange = (e) => {
        setComment(e.target.value);
    };
    const handleReportingIssue = async () => {
        const card = (cards || []).find(c => c.cardId === selectedCard);
        const request = {
            card: card,
            cardStatus: selectedIssueType,
            comment: comment
        };

        axios.post('/v1/card-issue/report', request)
            .then(({data: updatedCard}) => {
                updateCard(updatedCard)
                toast.success('Issue Reported.',{position: toast.POSITION.BOTTOM_CENTER});
            })
            .catch(e => toast.error(e.message, {position: toast.POSITION.BOTTOM_CENTER}))
            .finally(handleClose);
    };

    const cardOptions = (cards || []).map(({cardId, cardName, maskedCardNumber}) => {
        return (<option key={cardId} value={cardId}>{cardName} - {maskedCardNumber}</option>)
    });

    const cardStatusOptions = (cardStatuses || []).map(status => {
        return (<option key={status} value={status} className="text-lowercase text-capitalize">{status}</option>)
    });

    return(
        <div className="report-issue">
            <Button className="report-issue__btn" variant="link" onClick={handleShow}>Report Issue</Button>
            <Modal show={show}>
                <Modal.Header>
                    <Modal.Title>Report Card Issue</Modal.Title>
                    <CloseButton onClick={handleClose}/>
                </Modal.Header>
                <Modal.Body>
                    <Form className="report-issue__form">
                        <Form.Group className="mb-2">
                            <Form.Label>Card</Form.Label>
                            <Form.Select value={selectedCard} onChange={handleSelectedCardChange}>
                                <option>Select Card</option>
                                {cardOptions}
                            </Form.Select>
                        </Form.Group>
                        <Form.Group className="mb-2">
                            <Form.Label>Issue Type</Form.Label>
                            <Form.Select value={selectedIssueType} onChange={handleSelectedIssueChange}>
                                <option>Select Issue</option>
                                {cardStatusOptions}
                            </Form.Select>
                        </Form.Group>
                        <Form.Group className="mb-1">
                            <Form.Label>Comment</Form.Label>
                            <Form.Control value={comment} as="textarea" rows={3} onChange={handleCommentChange}/>
                        </Form.Group>
                    </Form>
                    <Form.Text className="report-issue__note fw-lighter fst-italic mx-2" muted>
                        * All lost or stolen cards will be deactivated.
                    </Form.Text>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>
                        Cancel
                    </Button>
                    <Button variant="danger"
                            disabled={!selectedIssueType || !selectedCard}
                            onClick={handleReportingIssue}>
                        Report Issue
                    </Button>
                </Modal.Footer>
            </Modal>
        </div>
    );
};
